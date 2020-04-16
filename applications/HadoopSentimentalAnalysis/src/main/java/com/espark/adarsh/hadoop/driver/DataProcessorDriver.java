package com.espark.adarsh.hadoop.driver;

import com.espark.adarsh.hadoop.mapper.DataProcessorMapper;
import com.espark.adarsh.hadoop.reducer.DataProcessorReducer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.net.URI;

public class DataProcessorDriver extends Configured implements Tool {

    public int run(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.printf("Usage: %s [generic options] <input> <output>\n", getClass().getSimpleName());
            ToolRunner.printGenericCommandUsage(System.err);
            return -1;
        }
        Job mapReduceJob = new Job();
        Configuration configuration= mapReduceJob.getConfiguration();

        // either below line or [ -D files /adarsh/hadoop/data/lookup.txt ] in the cmd line
        DistributedCache.addCacheFile(new URI("/adarsh/hadoop/data/lookup.txt"),configuration);
        mapReduceJob.setJarByClass(DataProcessorDriver.class);
        mapReduceJob.setJobName("MapReduceSentimentalAyalysisJob");

        // to delete the output directory if exist
        FileSystem fileSystem = FileSystem.get(new Configuration());
        fileSystem.delete(new Path(args[1]), true);

        // setting up the inout and out put hdfs path for jobs
        FileInputFormat.addInputPath(mapReduceJob, new Path(args[0]));
        FileOutputFormat.setOutputPath(mapReduceJob, new Path(args[1]));

        // setting up the output key type and output value types
        mapReduceJob.setOutputKeyClass(Text.class);
        mapReduceJob.setOutputValueClass(IntWritable.class);

        // setting up the output format type
        mapReduceJob.setOutputFormatClass(TextOutputFormat.class);

        // setting up the mapper and reducer classes
        mapReduceJob.setMapperClass(DataProcessorMapper.class);
        mapReduceJob.setReducerClass(DataProcessorReducer.class);

        int returnValue = mapReduceJob.waitForCompletion(true) ? 0 : 1;
        System.out.println("Map Reduce Job.isSuccessful :::=> " + mapReduceJob.isSuccessful());
        return returnValue;
    }
}
