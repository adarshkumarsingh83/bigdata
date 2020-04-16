package com.espark.adarsh.hadoop.util;

import org.apache.hadoop.fs.Path;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LookUpUtil {

    private static Map<String, String> localCache = new HashMap<String, String>();
    private static BufferedReader bufferedReaderl = null;

    public static void setDataToLookupCache(Path path) throws IOException {
        String line = null;
        try {
            bufferedReaderl = new BufferedReader(new FileReader(path.toString()));
            while ((line = bufferedReaderl.readLine()) != null) {
                String[] lookupWord = line.split(",");
                localCache.put(lookupWord[0], lookupWord[1]);
            }
            System.out.println(localCache);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bufferedReaderl.close();
        }
    }

    public static String getValueFromCache(String key) {
        return localCache.get(key.toString());
    }
}
