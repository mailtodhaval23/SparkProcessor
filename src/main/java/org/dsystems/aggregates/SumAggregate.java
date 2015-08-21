package org.dsystems.aggregates;

import java.io.Serializable;
import java.util.List;

public class SumAggregate extends Aggregate implements Serializable{

	static
	{
		AggregateFactory.instance().registerProduct("SUM", new SumAggregate());
	}
	
	@Override
	public Object aggregate(List<Object> values) {
		Double sum = 0.0;
		for(Object value: values) {
			Double v = 0.0;
			try {
				if (value != null)
					v = Double.parseDouble(value.toString());
			} catch (NumberFormatException nfe) {
				//Do nothing, its ok if value is not convertible to double. Ignore that value;
				System.out.println("Value not convertiable to number: " + value.toString());
			}
			sum += v;
		}
		return sum;
	}

	@Override
	public Aggregate createAggregate() {
		return new SumAggregate();
	}
}
