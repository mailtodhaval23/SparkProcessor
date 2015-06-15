package org.dsystems.stream;

import com.google.gson.annotations.SerializedName;


public class StreamConfig {

	@SerializedName("Name") 
	private String name;
	@SerializedName("Input") 
	private InputConfig inputConfig;
	@SerializedName("Parser") 
	private ParserConfig parserConfig;
	@SerializedName("Output") 
	private OutputConfig outputConfig;
	public OutputConfig getOutputConfig() {
		return outputConfig;
	}
	public void setOutputConfig(OutputConfig onputConfig) {
		this.outputConfig = onputConfig;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public InputConfig getInputConfig() {
		return inputConfig;
	}
	public void setInputConfig(InputConfig inputConfig) {
		this.inputConfig = inputConfig;
	}
	public ParserConfig getParserConfig() {
		return parserConfig;
	}
	public void setParserConfig(ParserConfig parserConfig) {
		this.parserConfig = parserConfig;
	}
	@Override
	public String toString() {
		return "StreamConfig [name=" + name + ", inputConfig=" + inputConfig
				+ ", parserConfig=" + parserConfig + ", outputConfig=" + outputConfig+ "]";
	}
	
}
