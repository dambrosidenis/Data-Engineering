package com.de.wc;

import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.shaded.com.fasterxml.jackson.databind.JsonNode;
import org.apache.hadoop.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hadoop.io.LongWritable;

public class Exercise_2_d_Mapper extends Mapper <LongWritable, Text, Text, DoubleWritable>
{
  private final Text outputKey = new Text();
  private final DoubleWritable outputValue = new DoubleWritable();

  public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException
  {
	  
	String[] parts = value.toString().split("\t", 2);
	if (parts.length == 2) {
		String json = parts[1];
				
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode examNode = objectMapper.readTree(json);
		
		JsonNode resultsNode = examNode.get("results");
		for (JsonNode result : resultsNode) {
			String studyplan = result.get("studyplan").asText();
			String grade = result.get("grade").asText();
			
			outputKey.set(studyplan);
			outputValue.set(Integer.parseInt(grade));
			context.write(outputKey, outputValue);
		}
		
	}
  }
}