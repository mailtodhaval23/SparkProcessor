package org.dsystems.output;

import java.io.Serializable;

import org.apache.spark.streaming.api.java.JavaDStream;
import org.dsystems.utils.Attributes;
import org.dsystems.utils.Record;
import org.dsystems.utils.ValidatorResponse;

public abstract class Output implements Serializable {

	public enum Type {FILE};
	public abstract ValidatorResponse init(Attributes attrs);
	//public abstract ValidatorResponse validate();
	public abstract void store(JavaDStream<Record> stream);
}
