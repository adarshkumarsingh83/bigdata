package com.espark.adarsh.hadoop.driver;

import com.espark.adarsh.hadoop.mapper.JsonProcssingMapper;
import com.espark.adarsh.hadoop.reducer.JsonProcessingReducer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class JsonProcessingDriver extends Configured implements Tool {

    public int run(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.printf("Usage: %s [generic options] <input> <output>\n", getClass().getSimpleName());
            ToolRunner.printGenericCommandUsage(System.err);
            return -1;
        }
        Configuration conf = new Configuration();

        Job mapReduceJob = new Job(conf);
        mapReduceJob.setJarByClass(JsonProcessingDriver.class);
        mapReduceJob.setJobName("MapReduceJsonProcessingJob");

        // to delete the output directory if exist
        FileSystem fileSystem = FileSystem.get(new Configuration());
        fileSystem.delete(new Path(args[1]), true);

        // setting up the inout and out put hdfs path for jobs
        FileInputFormat.addInputPath(mapReduceJob, new Path(args[0]));
        FileOutputFormat.setOutputPath(mapReduceJob, new Path(args[1]));

        // setting up the output key and value type from map
        mapReduceJob.setMapOutputKeyClass(Text.class);
        mapReduceJob.setMapOutputValueClass(Text.class);

        mapReduceJob.setOutputKeyClass(NullWritable.class);
        mapReduceJob.setOutputValueClass(Text.class);

        // setting up the input and output format type
        mapReduceJob.setInputFormatClass(TextInputFormat.class);
        mapReduceJob.setOutputFormatClass(TextOutputFormat.class);

        // setting up the mapper and reducer classes
        mapReduceJob.setMapperClass(JsonProcssingMapper.class);
        mapReduceJob.setReducerClass(JsonProcessingReducer.class);

        int returnValue = mapReduceJob.waitForCompletion(true) ? 0 : 1;
        System.out.println("Map Reduce Job.isSuccessful :::=> " + mapReduceJob.isSuccessful());
        return returnValue;
    }
}
