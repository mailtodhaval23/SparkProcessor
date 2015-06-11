package org.dsystems.stream;

import java.io.Serializable;

import org.apache.spark.streaming.api.java.JavaDStream;
import org.dsystems.parser.Parser;

public class DataStream<T>  implements Serializable{

	private static final long serialVersionUID = 1L;

	public static enum Type{NETWORK, KINESIS};
	
	private transient JavaDStream<T> stream;
	private Parser parser;	
	private String name;
	
	public DataStream(JavaDStream<T> stream) {
		this.setStream(stream);
	}

	public JavaDStream<T> getStream() {
		return stream;
	}

	public void setStream(JavaDStream<T> stream) {
		this.stream = stream;
	}

	public Parser getParser() {
		return parser;
	}

	public void setParser(Parser parser) {
		this.parser = parser;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


}