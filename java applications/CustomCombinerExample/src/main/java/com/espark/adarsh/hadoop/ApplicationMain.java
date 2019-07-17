package com.espark.adarsh.hadoop;

import com.espark.adarsh.hadoop.driver.WordCountDriver;
import org.apache.hadoop.util.ToolRunner;

public class ApplicationMain {

    public static void main(String[] args) throws Exception {
        int exitCode = ToolRunner.run(new WordCountDriver(), args);
        System.exit(exitCode);
    }
}
