package org.dsystems.processor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.dsystems.utils.Attributes;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.dsystems.parser.Parser;
import org.dsystems.parser.ParserFactory;
import org.dsystems.stream.DataStream;
import org.dsystems.stream.StreamFactory;
import org.dsystems.utils.Record;

public class SparkProcessor implements Serializable{


	private static final long serialVersionUID = 1L;
	private static JavaStreamingContext jsc = null;
	private static SparkProcessor sp;
	private List<DataStream> streams;
	private String name;

	private SparkProcessor() {
		this.streams = new ArrayList<DataStream>();
	}

	private SparkProcessor(String name) {
		this.streams = new ArrayList<DataStream>();
		this.name = name;
		SparkConf sparkConf = new SparkConf().setAppName(name);
	    jsc = new JavaStreamingContext(sparkConf, Durations.seconds(10));
	}

	private SparkProcessor(List<DataStream> streams, Parser parser) {
		this.streams = streams;
	}
	
	public static SparkProcessor CreateSparkProcessor(String name) {
		if (sp == null) {
			sp = new SparkProcessor(name);
		}
		return sp;
	}
	
	public boolean addStream(DataStream.Type streamType, Attributes streamAttrs, Parser.Type parserType, Attributes parserAttrs) {
		DataStream ds = StreamFactory.CreateStream(jsc, streamType, streamAttrs);
		Parser parser = ParserFactory.getParser(parserType, parserAttrs);
		ds.setParser(parser);
		this.streams.add(ds);
		return true;
	}

	public boolean start() {
		
		List<JavaDStream<Record>> records = new ArrayList<JavaDStream<Record>>();
		for (final DataStream<String> ds : streams) {
			@SuppressWarnings("unchecked")
			JavaDStream<Record> stream = ds.getStream().map(
					new Function<String, Record>() {
						private static final long serialVersionUID = 1L;

						// final Parser parser = ds.getParser();
						public Record call(String data) throws Exception {
							return ds.getParser().parse(data);
						}
					});

			stream.print();
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
