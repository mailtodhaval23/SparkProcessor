package org.dsystems.aggregates;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class AggregateFactory implements Serializable {

	private HashMap registredAggregates = new HashMap();
	private static AggregateFactory instance;
	
	private AggregateFactory(){
		registredAggregates = new HashMap();
	}
	
	public static AggregateFactory instance() {
		if(instance == null) {
			instance = new AggregateFactory();
		}
		return instance;
	}
	public void registerProduct(String type, Aggregate p)    {
		registredAggregates.put(type, p);
	}

	public Aggregate createAggregate(String type){
		Aggregate agg = (Aggregate) registredAggregates.get(type);
		if (agg != null) {
			return agg.createAggregate();
		} else {
			return createAggregatorFromClassName(type);
		}
	}

	private Aggregate createAggregatorFromClassName(String type) {
		try {
			Class aggregatorClass = Class.forName(type);
			Constructor aggregatorConstructor = aggregatorClass.getConstructor(null);
			Aggregator agg = (Aggregator) aggregatorConstructor.newInstance(null);
			return agg.getAggregate();
		} catch (ClassNotFoundException e) {
			System.out.println("Can not find class for aggreator: " + type);
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	
	/*public static Aggregate getAggregate(Aggregate.Type type) {
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
	}*/
}
