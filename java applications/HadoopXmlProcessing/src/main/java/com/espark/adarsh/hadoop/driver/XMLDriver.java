package com.espark.adarsh.hadoop.driver;

import com.espark.adarsh.hadoop.mapper.MyMapper;
import com.espark.adarsh.hadoop.util.XmlInputFormat;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.Tool;

public class XMLDriver extends Configured implements Tool {


    public int run(String[] args) throws Exception {


        Configuration conf = new Configuration();
         //conf.setInt(FixedLengthInputFormat.FIXED_RECORD_LENGTH, 2048);

        // OR alternatively you can set it this way, the name of the
        // property is
        // "mapreduce.input.fixedlengthinputformat.record.length"
        conf.setInt("mapreduce.input.fixedlengthinputformat.record.length",2048);
        String[] arg = new GenericOptionsParser(conf, args).getRemainingArgs();

        conf.set("START_TAG_KEY", "<employee>");
        conf.set("END_TAG_KEY", "</employee>");

        Job mapReduceJob = new Job(conf, "XML Processing Processing");
        try {
            mapReduceJob.setJarByClass(XMLDriver.class);
            mapReduceJob.setMapperClass(MyMapper.class);

            mapReduceJob.setNumReduceTasks(0);

            mapReduceJob.setInputFormatClass(XmlInputFormat.class);
            // mapReduceJob.setOutputValueClass(TextOutputFormat.class);

            mapReduceJob.setMapOutputKeyClass(Text.class);
            mapReduceJob.setMapOutputValueClass(LongWritable.class);

            mapReduceJob.setOutputKeyClass(Text.class);
            mapReduceJob.setOutputValueClass(LongWritable.class);

            FileInputFormat.addInputPath(mapReduceJob, new Path(args[0]));
            FileOutputFormat.setOutputPath(mapReduceJob, new Path(args[1]));

            mapReduceJob.waitForCompletion(true);

        } catch (Exception e) {
            //LogWriter.getInstance().WriteLog("Driver Error: " + e.getMessage());
            System.out.println(e.getMessage().toString());
        }

        int returnValue = mapReduceJob.waitForCompletion(true) ? 0 : 1;
        System.out.println("Map Reduce Job.isSuccessful :::=> " + mapReduceJob.isSuccessful());
        return returnValue;
    }

}

