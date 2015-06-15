package org.dsystems.stream;

import java.io.Serializable;

import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.dsystems.output.Output;
import org.dsystems.output.OutputFactory;
import org.dsystems.parser.Parser;
import org.dsystems.parser.ParserFactory;
import org.dsystems.utils.Attributes;

public class DataStream<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	public static enum Type {
		NETWORK, KINESIS
	};

	private transient JavaDStream<T> stream;
	private Parser parser;
	private Output output;
	private String name;

	//Constructor is private just to restrict the creation of DataStream through init method only.
	// It is NOT for making it singleton. 
	private DataStream() {
		
	}
	
	/*
	 * private DataStream(JavaStreamingContext jsc, StreamConfig streamConfig) {
	 * DataStream.Type streamType =
	 * DataStream.Type.valueOf(streamConfig.getInputConfig().getType());
	 * Attributes streamAttrs = streamConfig.getInputConfig().getAttributes();
	 * Parser.Type parserType =
	 * Parser.Type.valueOf(streamConfig.getParserConfig().getType()); Attributes
	 * parserAttrs = streamConfig.getParserConfig().getAttributes(); //return
	 * this.addStream(streamType, streamAttrs, parserType, parserAttrs); }
	 */

	public Output getOutput() {
		return output;
	}

	public void setOutput(Output output) {
		this.output = output;
	}

	/*private static DataStream initStream(JavaStreamingContext jsc,
			DataStream.Type streamType, Attributes streamAttrs,
			Parser.Type parserType, Attributes parserAttrs) {
		DataStream ds = StreamFactory
				.CreateStream(jsc, streamType, streamAttrs);
		Parser parser = ParserFactory.getParser(parserType, parserAttrs);
		if (parser == null)
			return null;
		ds.setParser(parser);
		// this.streams.add(ds);
		return ds;
	}
*/
	public DataStream(JavaDStream<T> stream) {
		this.setStream(stream);
	}

	public JavaDStream<T> getStream() {
		return stream;
	}

	public void setStream(JavaDStream<T> stream) {
		this.stream = stream;
	}

	public Parser getParser() {
		return parser;
	}

	public void setParser(Parser parser) {
		this.parser = parser;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private static DataStream getDataStream(JavaStreamingContext jsc,
			InputConfig inputConfig) {
		DataStream.Type streamType = DataStream.Type.valueOf(inputConfig
				.getType());
		Attributes streamAttrs = inputConfig.getAttributes();
		return StreamFactory.CreateStream(jsc, streamType, streamAttrs);
	}

	private static Parser getParser(ParserConfig parserConfig) {
		Parser.Type parserType = Parser.Type.valueOf(parserConfig.getType());
		Attributes parserAttrs = parserConfig.getAttributes();
		return ParserFactory.getParser(parserType, parserAttrs);
	}

	private static Output getOutput(OutputConfig outoutConfig) {
		Output.Type outputType = Output.Type.valueOf(outoutConfig.getType());
		Attributes outputAttrs = outoutConfig.getAttributes();
		return OutputFactory.getOutput(outputType, outputAttrs);
	}

	public static DataStream init(JavaStreamingContext jsc, StreamConfig streamConfig) {

		DataStream ds = getDataStream(jsc, streamConfig.getInputConfig());
		if (ds != null) {
			Parser parser = getParser(streamConfig.getParserConfig());
			if (parser != null) {
				ds.setParser(parser);
			} else {
				return null;
			}

			Output output = getOutput(streamConfig.getOutputConfig());
			if (output != null) {
				ds.setOutput(output);
			}
			// this.streams.add(ds);
			return ds;
		}
		return null;

	}

}