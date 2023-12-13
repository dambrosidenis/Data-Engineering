package com.de.wc;

import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;


public class Exercise_2_d_Reducer extends Reducer <Text, DoubleWritable, Text, DoubleWritable>
{
  private DoubleWritable count = new DoubleWritable();

  public void reduce(Text key, Iterable<DoubleWritable> values, Context context) throws IOException, InterruptedException
  {

    double valueSum = 0;
    double it = 0;
    for (DoubleWritable val : values)
    {
      valueSum += val.get();
      it++;
    }
    count.set(valueSum/it);
    context.write(key, count);
  }
}