package org.dsystems.output;

import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.dsystems.utils.Attributes;
import org.dsystems.utils.ValidatorResponse;

public class TableOutput extends Output {

	@Override
	public ValidatorResponse init(String streamName, Attributes attrs)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void store(String dirName, JavaDStream stream) {
		// TODO Auto-generated method stub

	}

	@Override
	public void store(String dirName, JavaPairDStream stream) {
		// TODO Auto-generated method stub

	}

}
