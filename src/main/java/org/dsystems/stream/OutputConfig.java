package org.dsystems.stream;

import org.dsystems.utils.Attributes;

import com.google.gson.annotations.SerializedName;

public class OutputConfig {
	@SerializedName("Type")
	private String type;
	@SerializedName("Properties")
	private Attributes attributes;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Attributes getAttributes() {
		return attributes;
	}
	public void setAttributes(Attributes attributes) {
		this.attributes = attributes;
	}
	@Override
	public String toString() {
		return "OutputConfig [type=" + type + ", attributes=" + attributes + "]";
	}
	
}
