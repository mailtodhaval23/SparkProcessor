package org.dsystems.aggregates;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.dsystems.aggregates.Aggregate.Type;
import org.dsystems.config.AggregateConfig;
import org.dsystems.config.AggregationConfig;
import org.dsystems.utils.Record;

import scala.Array;

public class Aggregation implements Serializable{

	private static final long serialVersionUID = 1L;
	private String[] key;
	private String name;
	private long windowDuration;
	private long slideDuration;
	private List<Aggregator> aggregators;

	public List<Aggregator> getAggregators() {
		return aggregators;
	}

	public void setAggregators(List<Aggregator> aggregators) {
		this.aggregators = aggregators;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public Record getAggregates(List<Record> records) {
		
		Record record = new Record();
		for (Aggregator agg: aggregators){
			Object value = agg.getAggregation(records);
			record.put(agg.getName(), value);
		}
		return record;
	}

	public String[] getKey() {
		return key;
	}

	public void setKey(String[] key) {
		this.key = key;
	}
	
	public static Aggregation createAggregation(AggregationConfig config) {
	
		System.out.println("Aggregation :: createAggregation(): config:" + config );
		Aggregation aggregation = new Aggregation();
		String[] keys = config.getKey().split(",");
		aggregation.setKey(keys);
		aggregation.name = config.getName();
		aggregation.windowDuration = config.getWindowDuration();
		aggregation.slideDuration = config.getSlideDuration();
		for(AggregateConfig agConfig: config.getAggregats()){
			aggregation.addAggregator(agConfig);
		}
		System.out.println("Aggregation :: createAggregation(): aggregation:" + aggregation.toString() );
		return aggregation;
	}
	
	public boolean addAggregator(AggregateConfig aggregateConfig){
		System.out.println("Aggregation :: addAggregator(): aggregateConfig:" + aggregateConfig.toString() );
		//Aggregate.Type type = Type.valueOf(aggregateConfig.getAggregateFunction());
		//Aggregate agg = AggregateFactory.getAggregate(type);
		Aggregate agg = AggregateFactory.instance().createAggregate(aggregateConfig.getAggregateFunction());
		if (agg == null)
			return false;
		if (this.aggregators == null)
			this.aggregators = new ArrayList<Aggregator>();
		Aggregator aggregator = new Aggregator();
		aggregator.setAggregate(agg);
		aggregator.setField(aggregateConfig.getField());
		aggregator.setName(aggregateConfig.getName());
		this.aggregators.add(aggregator);
		return true;
	}

	public long getWindowDuration() {
		return windowDuration;
	}

	public long getSlideDuration() {
		return slideDuration;
	}
}
