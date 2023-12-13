package com.de.wc;

import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;


public class Exercise_2_c_Reducer extends Reducer <Text, DoubleWritable, Text, DoubleWritable>
{
  private DoubleWritable count = new DoubleWritable();

  public void reduce(Text key, Iterable<DoubleWritable> values, Context context) throws IOException, InterruptedException
  {

    double minValue = 6;
    for (DoubleWritable val : values)
    {
      if (val.get() < minValue) {
    	  minValue = val.get();
      }
    }
    count.set(minValue);
    context.write(key, count);
  }
}