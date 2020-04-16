package com.espark.adarsh.hadoop;

import com.espark.adarsh.hadoop.driver.DataProcessorDriver;
import org.apache.hadoop.util.ToolRunner;

public class ApplicationMain {

    public static void main(String[] args) throws Exception {
        int exitCode = ToolRunner.run(new DataProcessorDriver(), args);
        System.exit(exitCode);
    }
}
