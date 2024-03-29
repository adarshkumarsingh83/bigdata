package com.espark.adarsh.hadoop.driver;

import com.espark.adarsh.hadoop.mapper.WordCountMapper;
import com.espark.adarsh.hadoop.reducer.WordCountReducer;
import com.espark.adarsh.hadoop.combiner.MyCombiner;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;

public class WordCountDriver extends Configured implements Tool {

    public int run(String[] cmdLineArguments) throws Exception {

        final Configuration configurationJob = new Configuration();

        final Job mapReduceWordCountJob = new Job(configurationJob, "MapReduceWordCountJob");
        mapReduceWordCountJob.setJarByClass(WordCountDriver.class);

        mapReduceWordCountJob.setOutputKeyClass(Text.class);
        mapReduceWordCountJob.setOutputValueClass(IntWritable.class);

        mapReduceWordCountJob.setMapperClass(WordCountMapper.class);
        // setting up the combiner
        mapReduceWordCountJob.setCombinerClass(MyCombiner.class);
        mapReduceWordCountJob.setReducerClass(WordCountReducer.class);

        mapReduceWordCountJob.setInputFormatClass(TextInputFormat.class);
        mapReduceWordCountJob.setOutputFormatClass(TextOutputFormat.class);

        // to delete the output directory if exist
        FileSystem fileSystem = FileSystem.get(new Configuration());
        fileSystem.delete(new Path(cmdLineArguments[1]), true);

        if (cmdLineArguments[2] == null) {
            FileInputFormat.addInputPath(mapReduceWordCountJob, new Path(cmdLineArguments[0]));
            FileOutputFormat.setOutputPath(mapReduceWordCountJob, new Path(cmdLineArguments[1]));
        }else{
            // if third argument is their then it will copy the file from lfs to hdfs first
            fileSystem.copyToLocalFile(new Path(cmdLineArguments[2]),new Path(cmdLineArguments[0]));
            FileInputFormat.addInputPath(mapReduceWordCountJob, new Path(cmdLineArguments[0]));
            FileOutputFormat.setOutputPath(mapReduceWordCountJob, new Path(cmdLineArguments[1]));
        }


        int returnValue = mapReduceWordCountJob.waitForCompletion(true) ? 0 : 1;
        System.out.println("Map Reduce Job.isSuccessful :::=> " + mapReduceWordCountJob.isSuccessful());
        return returnValue;
    }

}
