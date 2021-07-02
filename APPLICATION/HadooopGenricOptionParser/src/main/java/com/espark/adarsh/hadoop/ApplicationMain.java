package com.espark.adarsh.hadoop;

import com.espark.adarsh.hadoop.driver.GenericOptionParserDriver;
import org.apache.hadoop.util.ToolRunner;

public class ApplicationMain {


    /* hadoop jar hadoopwordcount-1.0-SNAPSHOT.jar com.espark.adarsh.hadoop.ApplicationMain
          -D mapred.reduce.tasks=4 /adarsh/hadoop/data/input.txt /adarsh/hadoop/data/wordcount */
    public static void main(String[] args) throws Exception {
        int exitCode = ToolRunner.run(new GenericOptionParserDriver(), args);
        System.exit(exitCode);
    }
}
