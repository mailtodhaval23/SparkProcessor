package org.dsystems.processors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dsystems.utils.Attributes;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFlatMapFunction;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.Time;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.dsystems.aggregates.Aggregation;
import org.dsystems.config.SparkProcessorConfig;
import org.dsystems.config.StreamConfig;
import org.dsystems.parser.Parser;
import org.dsystems.parser.ParserFactory;
import org.dsystems.rules.engine.RulesEngine;
import org.dsystems.stream.DataStream;
import org.dsystems.stream.StreamFactory;
import org.dsystems.utils.Record;

import scala.Tuple2;

public class SparkProcessor implements Serializable{


	private static final long serialVersionUID = 1L;
	private static JavaStreamingContext jsc = null;
	private static SparkProcessor sp;
	private List<DataStream> streams;
	private String name;
	
	/*private SparkProcessor() {
		this.streams = new ArrayList<DataStream>();
	}*/

	private SparkProcessor(String name) {
		initSparkContext(name, 1);
		this.streams = new ArrayList<DataStream>();
		this.name = name;
	}
	private SparkProcessor(String name, Attributes attrs) {
		this(name);
	    //initRulesEngine(attrs);
	}

	private void initSparkContext(String name, long duration) {
		SparkConf sparkConf = new SparkConf().setAppName(name);
	    jsc = new JavaStreamingContext(sparkConf, Durations.seconds(duration));
	}


	/*private SparkProcessor(List<DataStream> streams, Parser parser) {
		this.streams = streams;
	}*/
	public static boolean CreateSparkProcessor(SparkProcessorConfig config) {
		System.out.println("CreateSparkProcessor:: Config: " + config.toString());
		if (sp == null){
			sp = new SparkProcessor(config.getName());
			sp.name = config.getName();
			for (StreamConfig streamConfig: config.getStreamConfigs()){
				try {
					sp.addStream(streamConfig);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (sp.streams.size() > 0) {
				//sp.initRulesEngine(config.getRulesFileName(), config.getActionsFileName());
				return sp.start();
			}
		}
		return false;
	}
	
/*	public static SparkProcessor CreateSparkProcessor(String name, Attributes attrs) {
		if (sp == null) {
			sp = new SparkProcessor(name, attrs);
		}
		return sp;
	}
*/	
	public boolean addStream(StreamConfig streamConfig) throws Exception {
		DataStream ds = DataStream.init(jsc, streamConfig);
		System.out.println("SparkProcessor:: addStream: ds:"+ ds.toString());
		if (ds != null) {
			this.streams.add(ds);
			return true;
		}
		return false;
		//return new DataStream(streamConfig);
	}
	
	
	private boolean start() {
		
		List<JavaDStream<Record>> records = new ArrayList<JavaDStream<Record>>();
		//final RulesEngine rules = this.ruleEngine;
		for (final DataStream<String> ds : streams) {
			JavaDStream<Record> stream = ds.getStream().getStream().map(
					new Function<String, Record>() {
						private static final long serialVersionUID = 1L;

						// final Parser parser = ds.getParser();
						public Record call(String data) throws Exception {
							Record record = ds.getParser().parse(data);
							if (record != null && record.size() > 0){
								if (ds.getRules() != null)
									ds.getRules().run(record);
								return record;
							} else 
								return new Record();
						}
					});
			stream.print();
			if (ds.getOutput() != null) {
				ds.getOutput().store(ds.getName(), stream);
			}
			
			generateAggregateStreams(ds, stream);
		
			
			//stream.dstream().saveAsTextFiles("maprfs://sj-il-bmi-db1:7222/user/bmi/Spark/data", "data");
			//stream.dstream().saveAsTextFiles("file:///tmp/data", "data");
			//pairs.saveAsHadoopFiles(ds.getName(), ".data");
		}
		jsc.start();
		return true;
	}

	private void generateAggregateStreams(DataStream<String> ds, JavaDStream<Record> stream) {
		List<Aggregation> aggregations = ds.getAggregations();
		if (aggregations == null) 
			return;
		
		for (final Aggregation aggregation: aggregations) {
			
			JavaPairDStream<Record, Record> pairStream = stream.mapToPair(new PairFunction<Record, Record, Record>() {

				private static final long serialVersionUID = 1L;

				public Tuple2<Record, Record> call(Record record)
						throws Exception {
					Record key = record.getSubRecord(aggregation.getKey());
					return new Tuple2<Record, Record>(key, record);
				}
			});
			
			//JavaPairDStream<Record, Record> aggregagteStream = pairStream.
			Duration windowDuration = Durations.seconds(aggregation.getWindowDuration());
			Duration slideDuration = Durations.seconds(aggregation.getSlideDuration()) ;
			JavaPairDStream<Record, Iterable<Record>> groupStream = pairStream.groupByKeyAndWindow(windowDuration, slideDuration);
			groupStream.print();
			
			JavaPairDStream<Record, Record> aggregateStream = groupStream.mapValues(new Function<Iterable<Record>, Record>() {

				public Record call(Iterable<Record> record) throws Exception {
					Iterator<Record> itr = record.iterator();
					List<Record> records = new ArrayList<Record>();
					while(itr.hasNext()) {
						records.add(itr.next());
					}
					return aggregation.getAggregates(records);
					//return null;
				}
			});
			 aggregateStream.print();
			 if (ds.getOutput() != null) {
					ds.getOutput().store(aggregation.getName(), aggregateStream);
					//aggregateStream.sa
			}
			
		}
		
	}
	private Duration slideDuration() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
