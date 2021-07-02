package com.espark.adarsh.hadoop.reducer;

import java.io.IOException;
import java.util.Iterator;
import com.espark.adarsh.hadoop.util.LookUpUtil;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class DataProcessorReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

    private Text text=new Text();

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        Path[] distributedCacheFiles = DistributedCache.getLocalCacheFiles(context.getConfiguration());
        if (distributedCacheFiles[0].getName().toString().trim().equalsIgnoreCase("lookup.txt")) {
            LookUpUtil.setDataToLookupCache(distributedCacheFiles[0]);
        }
    }

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int sum = 0;
        Iterator<IntWritable> valuesIt = values.iterator();

        while (valuesIt.hasNext()) {
            sum = sum + valuesIt.next().get();
        }

        // skipping bad words lines
        if(LookUpUtil.checkValueFromCache(key.toString())){
            text.set(key);
            context.write(text, new IntWritable(sum));
        }
    }

}
