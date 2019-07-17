package com.espark.adarsh.hadoop.mapper;

import java.io.IOException;
import java.util.StringTokenizer;

import com.espark.adarsh.hadoop.counter.HadoopCustomCounter;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    private final static IntWritable one = new IntWritable(1);
    private Text word = new Text();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        StringTokenizer tokenizer = new StringTokenizer(line, " ");
        while (tokenizer.hasMoreTokens()) {
            String token=tokenizer.nextToken();
            word.set(token.substring(0,1));
            context.write(word, one);
            context.getCounter(HadoopCustomCounter.TOTAL_WORD).increment(1L);

            char firstChar=token.charAt(0);
            if(Character.isUpperCase(firstChar)){
                context.getCounter(HadoopCustomCounter.UPPER_CASE).increment(1L);
            }else if (Character.isLowerCase(firstChar)){
                context.getCounter(HadoopCustomCounter.LOWER_CASE).increment(1L);
            }else if(Character.isDigit(firstChar)){
                System.out.println("Char Start with Digit");
            }else{
                System.out.println("Char Start with Special Character");
            }
        }
    }
}
