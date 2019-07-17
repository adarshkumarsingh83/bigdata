package com.espark.adarsh.hadoop.driver;

import com.espark.adarsh.hadoop.combiner.MyCombiner;
import com.espark.adarsh.hadoop.mapper.WordCountMapper;
import com.espark.adarsh.hadoop.partitioner.MyPartitioner;
import com.espark.adarsh.hadoop.reducer.WordCountReducer;
import org.apache.hadoop.conf.Configuration;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import org.apache.hadoop.conf.Configured;

public class WordCountDriver  extends Configured implements org.apache.hadoop.util.Tool {

    @Override
    public int run(String[] args) throws Exception {

        final Configuration configurationJob = new Configuration();

        final Job mapReduceWordCountJob = new Job(configurationJob, "MapReduceWordCountJob");
        mapReduceWordCountJob.setJarByClass(WordCountDriver.class);

        // setting up the output key and value types
        mapReduceWordCountJob.setOutputKeyClass(Text.class);
        mapReduceWordCountJob.setOutputValueClass(IntWritable.class);

        // setting up the mapper and reducer
        mapReduceWordCountJob.setMapperClass(WordCountMapper.class);
        mapReduceWordCountJob.setReducerClass(WordCountReducer.class);

        // setting up the combiner
        mapReduceWordCountJob.setCombinerClass(MyCombiner.class);

        //setting up the custom petitioner
        mapReduceWordCountJob.setPartitionerClass(MyPartitioner.class);

        // setting up the input & output formatting classes
        mapReduceWordCountJob.setInputFormatClass(TextInputFormat.class);
        mapReduceWordCountJob.setOutputFormatClass(TextOutputFormat.class);

        // to delete the output directory if exist
        FileSystem fileSystem = FileSystem.get(new Configuration());
        fileSystem.delete(new Path(args[1]), true);

        if (args[2] == null) {
            FileInputFormat.addInputPath(mapReduceWordCountJob, new Path(args[0]));
            FileOutputFormat.setOutputPath(mapReduceWordCountJob, new Path(args[1]));
        }else{
            // if third argument is their then it will copy the file from lfs to hdfs first
            fileSystem.copyToLocalFile(new Path(args[2]),new Path(args[0]));
            FileInputFormat.addInputPath(mapReduceWordCountJob, new Path(args[0]));
            FileOutputFormat.setOutputPath(mapReduceWordCountJob, new Path(args[1]));
        }

        int returnValue = mapReduceWordCountJob.waitForCompletion(true) ? 0 : 1;
        System.out.println("Map Reduce Job.isSuccessful :::=> " + mapReduceWordCountJob.isSuccessful());
        return returnValue;
    }

}
