package org.dsystems.stream;

import java.util.List;

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
	@SerializedName("Aggregations")
	private List<AggregationConfig> aggregationConfigs;
	@SerializedName("RulesFile") 
	private String rulesFile;
	@SerializedName("ActionsFile") 
	private String actionsFile;
	@SerializedName("AggregateRulesFile") 
	private String aggregateRulesFile;
	

	public String getRulesFile() {
		return rulesFile;
	}
	public void setRulesFile(String rulesFile) {
		this.rulesFile = rulesFile;
	}
	public String getActionsFile() {
		return actionsFile;
	}
	public void setActionsFile(String actionsFile) {
		this.actionsFile = actionsFile;
	}
	public String getAggregateRulesFile() {
		return aggregateRulesFile;
	}
	public void setAggregateRulesFile(String aggregateRulesFile) {
		this.aggregateRulesFile = aggregateRulesFile;
	}
	
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
	public List<AggregationConfig> getAggregationConfigs() {
		return aggregationConfigs;
	}
	public void setAggregationConfigs(List<AggregationConfig> aggregationConfigs) {
		this.aggregationConfigs = aggregationConfigs;
	}
	@Override
	public String toString() {
		return "StreamConfig [name=" + name + ", inputConfig=" + inputConfig
				+ ", parserConfig=" + parserConfig + ", outputConfig="
				+ outputConfig + ", aggregationConfigs=" + aggregationConfigs
				+ ", rulesFile=" + rulesFile + ", actionsFile=" + actionsFile
				+ ", aggregateRulesFile=" + aggregateRulesFile + "]";
	}
	
	
	
}
