package com.espark.adarsh.hadoop.util;

import org.apache.hadoop.fs.Path;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LookUpUtil {

    private static Map<String, String> localCache = new HashMap<String, String>();
    private static BufferedReader bufferedReader = null;

    public static void setDataToLookupCache(Path path) throws IOException {
        String line = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(path.toString()));
            while ((line = bufferedReader.readLine()) != null) {
                 String dataLine = line.trim();
                localCache.put(dataLine,dataLine);
            }
            System.out.println("Local Cache Data :==> "+localCache);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bufferedReader.close();
        }
    }

    public static String getValueFromCache(String key) {
        return localCache.get(key.toString());
    }

    public static boolean checkValueFromCache(String key) {
        return localCache.containsKey(key.toString());
    }
}
