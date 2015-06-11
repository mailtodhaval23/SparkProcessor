package org.dsystems.stream;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.dsystems.utils.Attributes;

import org.apache.spark.api.java.StorageLevels;
import org.apache.spark.storage.StorageLevel;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kinesis.KinesisUtils;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.kinesis.AmazonKinesisClient;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.InitialPositionInStream;

public class StreamFactory  implements Serializable{


	private static final long serialVersionUID = 1L;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> DataStream<T> CreateStream(JavaStreamingContext ssc, DataStream.Type type,
			Attributes attr) {
		switch (type) {
			case NETWORK: {
				String  host = attr.getValue("host");
				int port = Integer.parseInt(attr.getValue("port"));
			    JavaReceiverInputDStream<String> data = ssc.socketTextStream(
			           host, port, StorageLevels.MEMORY_AND_DISK_SER);
				return new DataStream(data);	
			}
			case KINESIS: {
				return 	new DataStream( createKinesisStream(ssc, attr));
			}
			default:
				return null;
			}

	}

	private static JavaDStream<byte[]> createKinesisStream(JavaStreamingContext ssc, Attributes attr) {
	    // Populate the appropriate variables from the given args
	    String streamName = attr.getValue("StreamName");
	    String endpointUrl = attr.getValue("endpointUrl");
	    String accessKeyId = attr.getValue("");
	    String secretAccessKey = attr.getValue("secret_access_key");
	    Duration batchInterval = new Duration(Integer.parseInt(attr.getValue("duration")));

	    // Create a Kinesis client in order to determine the number of shards for the given stream
	    BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKeyId, secretAccessKey);
	    AmazonKinesisClient kinesisClient = new AmazonKinesisClient(awsCreds);
	    kinesisClient.setEndpoint(endpointUrl);
	    int numShards =
	        kinesisClient.describeStream(streamName).getStreamDescription().getShards().size();


	    // In this example, we're going to create 1 Kinesis Receiver/input DStream for each shard.
	    // This is not a necessity; if there are less receivers/DStreams than the number of shards,
	    // then the shards will be automatically distributed among the receivers and each receiver
	    // will receive data from multiple shards.
	    int numStreams = numShards;

	    // Get the region name from the endpoint URL to save Kinesis Client Library metadata in
	    // DynamoDB of the same region as the Kinesis stream
	    Duration kinesisCheckpointInterval = batchInterval;
	    // Create the Kinesis DStreams
	    List<JavaDStream<byte[]>> streamsList = new ArrayList<JavaDStream<byte[]>>(numStreams);
	    for (int i = 0; i < numStreams; i++) {
	      streamsList.add(
	          KinesisUtils.createStream(ssc, streamName, endpointUrl,
	        		  kinesisCheckpointInterval, InitialPositionInStream.LATEST, StorageLevel.MEMORY_AND_DISK_2())
	      );
	    }

	    // Union all the streams if there is more than 1 stream
	    JavaDStream<byte[]> unionStreams;
	    if (streamsList.size() > 1) {
	      unionStreams = ssc.union(streamsList.get(0), streamsList.subList(1, streamsList.size()));
	    } else {
	      // Otherwise, just use the 1 stream
	      unionStreams = streamsList.get(0);
	    }
		return unionStreams;

	}
}
