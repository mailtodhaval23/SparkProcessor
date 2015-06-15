package org.dsystems.processors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.dsystems.utils.Attributes;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFlatMapFunction;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.dsystems.parser.Parser;
import org.dsystems.parser.ParserFactory;
import org.dsystems.rules.engine.RulesEngine;
import org.dsystems.stream.DataStream;
import org.dsystems.stream.SparkProcessorConfig;
import org.dsystems.stream.StreamConfig;
import org.dsystems.stream.StreamFactory;
import org.dsystems.utils.Record;

import scala.Tuple2;

public class SparkProcessor implements Serializable{


	private static final long serialVersionUID = 1L;
	private static JavaStreamingContext jsc = null;
	private static SparkProcessor sp;
	private List<DataStream> streams;
	private String name;
	RulesEngine ruleEngine;

	/*private SparkProcessor() {
		this.streams = new ArrayList<DataStream>();
	}*/

	private SparkProcessor(String name) {
		initSparkContext(name);
		this.streams = new ArrayList<DataStream>();
		this.name = name;
	}
	private SparkProcessor(String name, Attributes attrs) {
		this(name);
	    initRulesEngine(attrs);
	}

	private void initSparkContext(String name) {
		SparkConf sparkConf = new SparkConf().setAppName(name);
	    jsc = new JavaStreamingContext(sparkConf, Durations.seconds(10));
	}

	private void initRulesEngine(Attributes attrs) {
		if (attrs.getValue("rules_file") != null) {
			this.initRulesEngine(attrs.getValue("rules_file"),attrs.getValue("actions_file"));
	    }
	}

	private void initRulesEngine(String rulesFile, String actionsFile){
		this.ruleEngine = RulesEngine.init(rulesFile,actionsFile);
	}
	private void initRulesEngine(String rulesFile){
		this.initRulesEngine(rulesFile, null);
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
				sp.addStream(streamConfig);
			}
			if (sp.streams.size() > 0) {
				sp.initRulesEngine(config.getRulesFileName(), config.getActionsFileName());
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
	public boolean addStream(StreamConfig streamConfig) {
		DataStream ds = DataStream.init(jsc, streamConfig);
		if (ds != null) {
			this.streams.add(ds);
			return true;
		}
		return false;
		//return new DataStream(streamConfig);
		
	}
	
	
	private boolean start() {
		
		List<JavaDStream<Record>> records = new ArrayList<JavaDStream<Record>>();
		final RulesEngine rules = this.ruleEngine;
		for (final DataStream<String> ds : streams) {
			@SuppressWarnings("unchecked")
			JavaDStream<Record> stream = ds.getStream().map(
					new Function<String, Record>() {
						private static final long serialVersionUID = 1L;

						// final Parser parser = ds.getParser();
						public Record call(String data) throws Exception {
							Record record = ds.getParser().parse(data);
							if (rules != null)
								rules.run(record);
							return record;
						}
					});
			stream.print();
			if (ds.getOutput() != null) {
				ds.getOutput().store(stream);
			}
			
			//stream.dstream().saveAsTextFiles("maprfs://sj-il-bmi-db1:7222/user/bmi/Spark/data", "data");
			//stream.dstream().saveAsTextFiles("file:///tmp/data", "data");
			//pairs.saveAsHadoopFiles(ds.getName(), ".data");
		}
		jsc.start();
		return true;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
