package com.de.wc;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Exercise_2_d
{
  public static void main(String[] args) throws Exception
  {
    Configuration conf = new Configuration();
    Job wcJob = Job.getInstance(conf, "MapReduce WordCount");
    wcJob.setJar("Exercise_2_d.jar");
    wcJob.setMapperClass(Exercise_2_d_Mapper.class);
    wcJob.setCombinerClass(Exercise_2_d_Reducer.class);
    wcJob.setReducerClass(Exercise_2_d_Reducer.class);
    wcJob.setOutputKeyClass(Text.class);
    wcJob.setOutputValueClass(DoubleWritable.class);
    FileInputFormat.addInputPath(wcJob, new Path("/input"));
    FileOutputFormat.setOutputPath(wcJob, new Path("/output_2d"));
    System.exit(wcJob.waitForCompletion(true) ? 0 : 1);
  }
}