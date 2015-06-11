package org.dsystems.streamtest;

import org.dsystems.stream.InputConfig;
import org.dsystems.stream.ParserConfig;
import org.dsystems.stream.StreamConfig;
import org.dsystems.utils.Attributes;
import org.junit.Test;

import com.google.gson.Gson;

public class TestStreamConfig {

	@Test
	public void test() {
		StreamConfig config = new StreamConfig();
		InputConfig ip = new InputConfig();
		ip.setType("NETWORK");
		Attributes attrIp =  new Attributes();
		attrIp.put("host", "localhost");
		attrIp.put("port", 9999);
		ip.setAttributes(attrIp);
		
		ParserConfig pc = new ParserConfig();
		pc.setType("CSV");
		pc.setAttributes(attrIp);
		
		config.setInputConfig(ip);
		config.setParserConfig(pc);
		config.setName("NETWORK_TEMPERATURE_STREAM");
		
		Gson gson = new Gson();
		System.out.println("Config JSON: " + gson.toJson(config));
		
		String jsonstr = "{\"Name\":\"NETWORK_TEMPERATURE_STREAM\",\"Input\":{\"type\":\"NETWORK\",\"attributes\":{\"port\":9999,\"host\":\"localhost\"}},\"Parser\":{\"type\":\"CSV\",\"attributes\":{\"port\":9999,\"host\":\"localhost\"}}}";
		
		StreamConfig c1 = gson.fromJson(jsonstr, StreamConfig.class);
		System.out.println("Stream Config as Object: " + c1.toString());
	}

}
