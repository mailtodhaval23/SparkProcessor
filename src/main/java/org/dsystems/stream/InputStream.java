package org.dsystems.stream;

import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.dstream.DStream;
import org.dsystems.utils.Attributes;
import org.dsystems.utils.ValidatorResponse;

import scala.reflect.ClassTag;

public abstract class InputStream {

	protected transient JavaDStream stream;
	
	protected InputStream(JavaDStream stream) {
		this.stream = stream;
	}
	
	//public abstract InputStream createStream(Attributes attrs);

	public JavaDStream getStream() {
		return stream;
	}

	public void setStream(JavaDStream stream) {
		this.stream = stream;
	}

	public abstract InputStream init(JavaStreamingContext ssc, Attributes attrs) throws Exception;
}
