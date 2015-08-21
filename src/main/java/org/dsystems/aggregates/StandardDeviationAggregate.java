package org.dsystems.aggregates;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

public class StandardDeviationAggregate extends Aggregate implements Serializable{

	private static final long serialVersionUID = 1L;

	static
	{
		AggregateFactory.instance().registerProduct("STD_DEV", new StandardDeviationAggregate());
	}
	
	@Override
	public Object aggregate(List<Object> values) {
		
		double[] data = new double[values.size()];
		int ctr = 0;
		for(Object value: values) {
			Double v = 0.0;
			try {
				if (value!= null) {
					v = Double.parseDouble(value.toString());
				}
				data[ctr] = v;
			} catch (NumberFormatException nfe) {
				//Do nothing, its ok if value is not convertible to double. Ignore that value;
				System.out.println("Value not convertiable to number: " + value.toString());
			}
		}
		return calculateSD(data);
	}
	
	private static double calculateSD(double[] values) {
		StandardDeviation sd = new StandardDeviation();
		return sd.evaluate(values);
	}

	@Override
	public Aggregate createAggregate() {
		return new StandardDeviationAggregate();
	}
}
