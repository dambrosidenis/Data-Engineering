package com.de.wc;

import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.shaded.com.fasterxml.jackson.databind.JsonNode;
import org.apache.hadoop.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hadoop.io.LongWritable;

public class Exercise_2_b_Mapper extends Mapper <LongWritable, Text, Text, DoubleWritable>
{
  private final Text outputKey = new Text();
  private final DoubleWritable outputValue = new DoubleWritable();

  public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException
  {
	  
	String[] parts = value.toString().split("\t", 2);
	if (parts.length == 2) {
		String examCode = parts[0];
		String json = parts[1];
				
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode examNode = objectMapper.readTree(json);
		
		String examDate = examNode.get("date").asText();
		JsonNode resultsNode = examNode.get("results");
		for (JsonNode result : resultsNode) {
			String matno = result.get("matno").asText();
			String grade = result.get("grade").asText();
			
			outputKey.set(examCode);
			outputValue.set(Integer.parseInt(grade));
			context.write(outputKey, outputValue);
		}
		
	}
  }
}