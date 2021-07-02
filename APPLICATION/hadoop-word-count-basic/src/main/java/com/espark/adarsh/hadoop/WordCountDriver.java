package com.espark.adarsh.hadoop;

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

public class WordCountDriver {

    public static void main(String[] args) throws Exception {

        final Configuration configurationJob = new Configuration();

        final Job mapReduceWordCountJob = new Job(configurationJob, "MapReduceWordCountJob");
        mapReduceWordCountJob.setJarByClass(WordCountDriver.class);

        mapReduceWordCountJob.setOutputKeyClass(Text.class);
        mapReduceWordCountJob.setOutputValueClass(IntWritable.class);

        mapReduceWordCountJob.setMapperClass(WordCountMapper.class);
        mapReduceWordCountJob.setReducerClass(WordCountReducer.class);

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


        mapReduceWordCountJob.waitForCompletion(true);
    }

}
