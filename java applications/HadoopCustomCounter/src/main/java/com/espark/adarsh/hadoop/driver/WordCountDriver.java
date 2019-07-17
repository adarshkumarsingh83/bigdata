package com.espark.adarsh.hadoop.driver;

import com.espark.adarsh.hadoop.counter.HadoopCustomCounter;
import com.espark.adarsh.hadoop.mapper.WordCountMapper;
import com.espark.adarsh.hadoop.reducer.WordCountReducer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class WordCountDriver extends Configured implements Tool {

    public int run(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.printf("Usage: %s [generic options] <input> <output>\n", getClass().getSimpleName());
            ToolRunner.printGenericCommandUsage(System.err);
            return -1;
        }
        Configuration configuration = new Configuration();
        Job mapReduceJob = new Job(configuration);
        mapReduceJob.setJarByClass(WordCountDriver.class);
        mapReduceJob.setJobName("MapReduceCounterJob");

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
        mapReduceJob.setMapperClass(WordCountMapper.class);
        mapReduceJob.setReducerClass(WordCountReducer.class);

        int returnValue = mapReduceJob.waitForCompletion(true) ? 0 : 1;
        System.out.println("Map Reduce Job.isSuccessful :::=> " + mapReduceJob.isSuccessful());

        Counter totalWordCounter = mapReduceJob.getCounters().findCounter(HadoopCustomCounter.TOTAL_WORD);
        Counter  upperCaseWordCounter = mapReduceJob.getCounters().findCounter(HadoopCustomCounter.UPPER_CASE);
        Counter  lowerCaseWordCounter = mapReduceJob.getCounters().findCounter(HadoopCustomCounter.LOWER_CASE);
        System.out.println("Total Word is "+totalWordCounter.getValue());
        System.out.println("Total Upper Case Word is "+upperCaseWordCounter.getValue());
        System.out.println("Total Lower Case Word is "+lowerCaseWordCounter.getValue());

        return returnValue;
    }
}
