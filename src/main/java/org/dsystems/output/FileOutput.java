package org.dsystems.output;

import java.io.Serializable;

import org.apache.spark.streaming.api.java.JavaDStream;
import org.dsystems.utils.Attributes;
import org.dsystems.utils.Record;
import org.dsystems.utils.ValidatorResponse;

public class FileOutput extends Output implements Serializable {

	private String directory;
	private String extension;
	@Override
	public ValidatorResponse init(Attributes attrs) {
		
		boolean isValid = false;
		String message = "";
		if (attrs != null && attrs.getValue("directory") != null) {
			isValid = true;
			setDirectory(attrs.getValue("directory")) ;
			extension =  attrs.getValue("extension");
			if (extension == null) {
				extension = "data";
			}
		} else {
			isValid = false;
			message = "Required property directory not defined!!!";
		}
		ValidatorResponse vr = new ValidatorResponse(isValid, message);
		return vr;
	}


	public String getDirectory() {
		return directory;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}


	@Override
	public void store(JavaDStream stream) {
		stream.dstream().saveAsTextFiles(directory, extension);
		
	}

}
