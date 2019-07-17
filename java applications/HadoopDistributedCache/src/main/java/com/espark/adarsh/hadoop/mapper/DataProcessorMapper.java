package com.espark.adarsh.hadoop.mapper;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class DataProcessorMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    private Text word = new Text();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        if (value != null && value.toString().contains(",")) {
            String[] words = value.toString().split(",");
            word.set(words[0]);
            System.out.println(value);
            context.write(word, new IntWritable(Integer.parseInt(words[2].trim())));
        }
    }
}
