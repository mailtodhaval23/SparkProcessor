package org.dsystems.stream;

import java.util.List;

import org.dsystems.stream.StreamConfig;

import com.google.gson.annotations.SerializedName;

public class SparkProcessorConfig {

	@SerializedName("Name")
	private String name;

	@SerializedName("Streams")
	private List<StreamConfig> streamConfigs;
	
	@SerializedName("RulesFile")
	private String rulesFileName;
	
	@SerializedName("ActionsFile")
	private String actionsFileName;
	
	
	@Override
	public String toString() {
		return "SparkProcessorConfig [name=" + name + ", streamConfigs="
				+ streamConfigs + ", rulesFileName=" + rulesFileName
				+ ", actionsFileName=" + actionsFileName + "]";
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<StreamConfig> getStreamConfigs() {
		return streamConfigs;
	}
	public void setStreamConfigs(List<StreamConfig> streamConfigs) {
		this.streamConfigs = streamConfigs;
	}
	public String getRulesFileName() {
		return rulesFileName;
	}
	public void setRulesFileName(String rulesFileName) {
		this.rulesFileName = rulesFileName;
	}
	public String getActionsFileName() {
		return actionsFileName;
	}
	public void setActionsFileName(String actionsFileName) {
		this.actionsFileName = actionsFileName;
	}
	
}
