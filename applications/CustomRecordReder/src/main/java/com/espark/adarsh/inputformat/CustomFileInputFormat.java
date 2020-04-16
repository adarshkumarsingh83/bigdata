package com.espark.adarsh.inputformat;

import java.io.IOException;

import com.espark.adarsh.recordreader.CustomLineRecordReader;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

public class CustomFileInputFormat extends FileInputFormat<LongWritable,Text>{

	@Override
	public RecordReader<LongWritable, Text> createRecordReader(
			InputSplit split, TaskAttemptContext context) throws IOException,
			InterruptedException {
		
		return new CustomLineRecordReader();
	}
}