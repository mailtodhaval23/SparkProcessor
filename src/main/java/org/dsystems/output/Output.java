package org.dsystems.output;

import java.io.Serializable;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.dsystems.utils.Attributes;
import org.dsystems.utils.ValidatorResponse;

public abstract class Output implements Serializable {

	public enum Type {FILE};
	public abstract ValidatorResponse init(String streamName, Attributes attrs) throws Exception;
	//public abstract ValidatorResponse validate();
	public abstract void store(String dirName, JavaDStream stream);
	public abstract void store(String dirName, JavaPairDStream stream);
}
