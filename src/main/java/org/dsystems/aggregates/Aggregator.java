package org.dsystems.aggregates;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.dsystems.utils.Record;

public class Aggregator implements Serializable{

	private String name;
	private Aggregate aggregate;
	private String field;
	
	public Object getAggregation (List<Record> records) {
		List<Object> values = new ArrayList<Object>();
		for (Record record: records) {
			Object value = record.get(field);
			if (value != null)
				values.add(value);
		}
		return aggregate.aggregate(values);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Aggregate getAggregate() {
		return aggregate;
	}
	public void setAggregate(Aggregate aggregate) {
		this.aggregate = aggregate;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	
	
}
