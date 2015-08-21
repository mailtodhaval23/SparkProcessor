package org.dsystems.aggregates;

import java.io.Serializable;
import java.util.List;

public class AverageAggregate extends Aggregate implements Serializable{

	static
	{
		AggregateFactory.instance().registerProduct("AVERAGE", new AverageAggregate());
	}
	@Override
	public Object aggregate(List<Object> values) {
		Double sum = 0.0;
		int count = 0;
		for(Object value: values) {
			Double v = 0.0;
			try {
				if (value!=null) {
					v = Double.parseDouble(value.toString());
					count++;
				}
			} catch (NumberFormatException nfe) {
				//Do nothing, its ok if value is not convertible to double. Ignore that value;
				System.out.println("Value not convertiable to number: " + value.toString());
			}
			sum += v;
		}
		if (count != 0)
			return sum/count;
		else 
			return "NA";
	}
	@Override
	public Aggregate createAggregate() {
		return new AverageAggregate();
	}
}
