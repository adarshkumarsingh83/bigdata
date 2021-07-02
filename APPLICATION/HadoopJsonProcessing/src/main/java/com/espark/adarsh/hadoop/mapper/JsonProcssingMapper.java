package com.espark.adarsh.hadoop.mapper;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;


public class JsonProcssingMapper extends Mapper<LongWritable, Text, Text, Text> {

    public void map(LongWritable key, Text value, Context context)
            throws IOException, InterruptedException {
        String author;
        String book;
        String line = value.toString();
        String[] tuple = line.split("\\n");
        try {
            for (int i = 0; i < tuple.length; i++) {
                JSONObject obj = new JSONObject(tuple[i]);
                author = obj.getString("name");
                book = obj.getString("article");
                context.write(new Text(author), new Text(book));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
