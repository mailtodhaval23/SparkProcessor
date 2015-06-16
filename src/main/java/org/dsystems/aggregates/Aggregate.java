package org.dsystems.aggregates;

import java.io.Serializable;
import java.util.List;


public abstract class Aggregate implements Serializable{

	public enum Type {SUM, AVERAGE, MEAN, MEDIAN, SD};
	public abstract Object aggregate(List<Object> records);
	
}
