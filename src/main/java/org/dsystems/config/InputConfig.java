package org.dsystems.config;

import org.dsystems.utils.Attributes;

import com.google.gson.annotations.SerializedName;

public class InputConfig {
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
		return "InputConfig [type=" + type + ", attributes=" + attributes + "]";
	}
	
}
