package org.dsystems.sparkexamples;

import org.dsystems.utils.Attributes;

import org.dsystems.parser.CSVParser;
import org.dsystems.parser.Parser;
import org.dsystems.parser.ParserFactory;
import org.dsystems.processor.SparkProcessor;
import org.dsystems.stream.DataStream.Type;

public class RunSparkProcessor {

	public static void main(String[] args) {
		
		//DataStream<String> ds = StreamFactory.CreateStream(ssc, DataStream.Type.NETWORK , attrs);
		SparkProcessor sp = SparkProcessor.CreateSparkProcessor("RunSparkProcessor");
		Attributes streamAttrs = new Attributes();
		streamAttrs.putValue("host", "localhost");
		streamAttrs.putValue("port", "9999");
		
		Attributes parserAttrs = new Attributes();
		parserAttrs.putValue(CSVParser.FIELDS, "TimeStamp,Location,Temperature");
		
		
		sp.addStream(Type.NETWORK, streamAttrs, Parser.Type.CSV, parserAttrs);
		sp.start();
	}
}
