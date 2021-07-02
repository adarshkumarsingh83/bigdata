package com.espark.adarsh;

import com.espark.adarsh.driver.ApplicationDriver;
import org.apache.hadoop.util.ToolRunner;

public class ApplicationMain {

    /**
     * Main function which calls the run method and passes the args using ToolRunner
     * @param args Two arguments input and output file paths
     * @throws Exception
     */
    public static void main(String[] args) throws Exception{
        int exitCode = ToolRunner.run(new ApplicationDriver(), args);
        System.exit(exitCode);
    }
}
