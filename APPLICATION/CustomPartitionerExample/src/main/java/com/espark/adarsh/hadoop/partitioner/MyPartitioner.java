package com.espark.adarsh.hadoop.partitioner;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.Partitioner;
import org.apache.hadoop.mapreduce.lib.partition.HashPartitioner;


public class MyPartitioner extends HashPartitioner<Text, IntWritable> {

    @Override
    public int getPartition(Text text, IntWritable intWritable, int i) {
        return super.getPartition(text,intWritable,i);
    }
}
