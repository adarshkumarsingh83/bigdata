package com.espark.adarsh.hadoop.combiner;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Iterator;


public class MyCombiner extends Reducer<Text, IntWritable, Text, IntWritable> {

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {

        int sum = 0;
        final Iterator<IntWritable> intWritableIterator = values.iterator();
        while (intWritableIterator.hasNext()) {
            sum += intWritableIterator.next().get();
        }
        context.write(key, new IntWritable(sum));
    }
}