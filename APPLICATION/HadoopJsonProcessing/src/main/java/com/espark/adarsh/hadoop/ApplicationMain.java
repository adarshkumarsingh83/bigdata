package com.espark.adarsh.hadoop;

import com.espark.adarsh.hadoop.driver.JsonProcessingDriver;
import org.apache.hadoop.util.ToolRunner;

public class ApplicationMain {

    public static void main(String[] args) throws Exception {
        int exitCode = ToolRunner.run(new JsonProcessingDriver(), args);
        System.exit(exitCode);
    }
}
