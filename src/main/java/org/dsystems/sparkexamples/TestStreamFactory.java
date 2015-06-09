package org.dsystems.sparkexamples;


import org.dsystems.utils.Attributes;

import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.dsystems.stream.DataStream;
import org.dsystems.stream.StreamFactory;


public class TestStreamFactory {

	public static void main(String[] args) {
		testStreamFactory();
	}

	private static void testStreamFactory() {
		Attributes attr = new Attributes();
		attr.putValue("host", "localhost");
		attr.putValue("port", "9999");
	    SparkConf sparkConf = new SparkConf().setAppName("StreamFactoryTest");
	    JavaStreamingContext ssc = new JavaStreamingContext(sparkConf, Durations.seconds(10));

		DataStream<?> stream = StreamFactory.CreateStream(ssc, DataStream.Type.NETWORK, attr);
		attr.putValue("port", "9998");
		DataStream<?> stream8 = StreamFactory.CreateStream(ssc, DataStream.Type.NETWORK, attr);
		
		System.out.println("Printing current batch data: ============= ");
		stream.getStream().print();
		stream8.getStream().print();
		System.out.println("============================ End current batch data.");
		
		ssc.start();
	}
	
}
