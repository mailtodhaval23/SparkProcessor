package org.dsystems.stream;

import com.google.gson.annotations.SerializedName;

public class AggregateConfig {

	/*
	 {
        "Name": "SumOfTemperature",
        "Field": "Temperature",
        "Function": "SUM"
     }
	 */
	
	@SerializedName("Name") 
	private String name;
	@SerializedName("Field") 
	private String field;
	@SerializedName("Function") 
	private String aggregateFunction;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getAggregateFunction() {
		return aggregateFunction;
	}
	public void setAggregateFunction(String aggregateFunction) {
		this.aggregateFunction = aggregateFunction;
	}
	
	
}
