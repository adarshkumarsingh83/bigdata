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
        String myKey = key.toString() + ", " + LookUpUtil.getValueFromCache(key.toString());
        context.write(new Text(myKey), new IntWritable(sum));
    }

}
