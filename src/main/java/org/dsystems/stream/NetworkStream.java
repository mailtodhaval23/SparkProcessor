package org.dsystems.stream;

import org.apache.spark.api.java.StorageLevels;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.dsystems.utils.Attributes;

public class NetworkStream extends InputStream {

	static
	{
		StreamFactory.instance().registerProduct("NETWORK", new NetworkStream());
	}
	
	protected NetworkStream(JavaDStream stream) {
		super(stream);
		// TODO Auto-generated constructor stub
	}

	public NetworkStream() {
		super(null);
	}

	@Override
	public InputStream init(JavaStreamingContext ssc,Attributes attrs) {
		if (attrs != null &&  attrs.getValue("host")!= null && attrs.getValue("port") != null) {
			String  host = attrs.getValue("host");
			int port = Integer.parseInt(attrs.getValue("port"));
		    JavaReceiverInputDStream<String> data = ssc.socketTextStream(
		           host, port, StorageLevels.MEMORY_AND_DISK_SER);
			//this(data);
		    return new NetworkStream(data);
		} else {
			return null;
		}
		
	}

}
