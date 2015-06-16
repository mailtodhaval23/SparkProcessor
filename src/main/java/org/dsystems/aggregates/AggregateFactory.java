package org.dsystems.aggregates;

import java.io.Serializable;

public class AggregateFactory implements Serializable {

	public static Aggregate getAggregate(Aggregate.Type type) {
		switch(type) {
		case SUM: {
			return new SumAggregate();
		}
		case AVERAGE: {
			return new AverageAggregate();
		}
		default:
			return null;
		}
	}
}
