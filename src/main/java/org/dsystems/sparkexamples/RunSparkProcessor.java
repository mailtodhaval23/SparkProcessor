package org.dsystems.sparkexamples;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.dsystems.utils.Attributes;
import org.dsystems.config.SparkProcessorConfig;
import org.dsystems.config.StreamConfig;
import org.dsystems.parser.CSVParser;
import org.dsystems.parser.Parser;
import org.dsystems.parser.ParserFactory;
import org.dsystems.processors.SparkProcessor;
import org.dsystems.stream.DataStream.Type;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

public class RunSparkProcessor {

	static
	{
		try
		{
			Class.forName("org.dsystems.aggregates.SumAggregate");
			Class.forName("org.dsystems.aggregates.AverageAggregate");
			Class.forName("org.dsystems.aggregates.StandardDeviationAggregate");
			Class.forName("org.dsystems.stream.NetworkStream");
		}
		catch (ClassNotFoundException any)
		{
			any.printStackTrace();
		}
	}
	public static void main(String[] args) {
		
		//DataStream<String> ds = StreamFactory.CreateStream(ssc, DataStream.Type.NETWORK , attrs);
		//Attributes attrs = new Attributes();
/*		String rulesFile="C:\\Dhaval\\Work\\EasyRules\\Rules\\src\\test\\java\\App1.rls";
		String actionsFile="C:\\Dhaval\\Work\\EasyRules\\Rules\\src\\test\\java\\Actions.json";
*/		/*String rulesFile="/home/bmi/SparkWork/App1.rls";
		String actionsFile="/home/bmi/SparkWork/Actions.json";
		SparkProcessorConfig spConfig = new SparkProcessorConfig();
		spConfig.setName("RunSparkProcessor Example");
		List<StreamConfig> streamConfigs = new ArrayList<StreamConfig>();
		Gson gson = new Gson();
		String jsonstr = "{\"Name\":\"NETWORK_TEMPERATURE_STREAM\","
				+ "\"Input\":{\"Type\":\"NETWORK\",\"Properties\":{\"port\":\"9999\",\"host\":\"localhost\"}},"
				+ "\"Parser\":{\"Type\":\"CSV\"}}";
		StreamConfig streamConfig = gson.fromJson(jsonstr, StreamConfig.class);
		streamConfigs.add(streamConfig);
		spConfig.setStreamConfigs(streamConfigs);
		spConfig.setRulesFileName(rulesFile);
		spConfig.setActionsFileName(actionsFile);
		//attrs.put("rules_file", rulesFile);
*/		//attrs.put("actions_file", actionsFile);
		
		System.out.println("Config file: " + args[0]);
		Gson gson = new Gson();
		JsonReader jsonReader = null;
		try {
			jsonReader = new JsonReader(new FileReader(args[0]));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SparkProcessorConfig spConfig = gson.fromJson(jsonReader, SparkProcessorConfig.class);
		System.out.println("SparkProcessorConfig : " + gson.toJson(spConfig));
		//SparkProcessor sp = SparkProcessor.CreateSparkProcessor("RunSparkProcessor", attrs);
		SparkProcessor.CreateSparkProcessor(spConfig);
		
/*		Attributes streamAttrs = new Attributes();
		streamAttrs.putValue("host", "localhost");
		streamAttrs.putValue("port", "9999");
		
		Attributes parserAttrs = new Attributes();
		parserAttrs.putValue(CSVParser.FIELDS, "TimeStamp,Location,Temperature");
		
		
		sp.addStream(Type.NETWORK, streamAttrs, Parser.Type.CSV, parserAttrs);
*/		
		//sp.start();
	}
}
