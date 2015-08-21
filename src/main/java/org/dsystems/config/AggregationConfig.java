package org.dsystems.config;

import java.util.Arrays;

import com.google.gson.annotations.SerializedName;

public class AggregationConfig {

	/*
	 "Aggregations": [
        {
          "GroupBy": "Location",
          "Aggregates": [
            {
              "Name": "SumOfTemperature",
              "Field": "Temperature",
              "Function": "SUM"
            },
            {
              "Name": "AverageOfTemperature",
              "Field": "Temperature",
              "Function": "AVG"
            }
          ]
        }
      ]
	 */

	@SerializedName("Name") 
	private String name;
	@SerializedName("GroupBy") 
	private String key;
	@SerializedName("Window Duration")
	private long windowDuration;
	@SerializedName("Slide Duration")
	private long slideDuration;
	@SerializedName("Aggregates") 
	private AggregateConfig[] aggregats;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public AggregateConfig[] getAggregats() {
		return aggregats;
	}
	public void setAggregats(AggregateConfig[] aggregats) {
		this.aggregats = aggregats;
	}
	
	public long getWindowDuration() {
		return windowDuration;
	}
	public void setWindowDuration(long windowDuration) {
		this.windowDuration = windowDuration;
	}
	public long getSlideDuration() {
		return slideDuration;
	}
	public void setSlideDuration(long slideDuration) {
		this.slideDuration = slideDuration;
	}
	@Override
	public String toString() {
		return "AggregationConfig [key=" + key + ", aggregats="
				+ Arrays.toString(aggregats) + "]";
	}
	
}
