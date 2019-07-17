package com.espark.pig.udf.string;


import org.apache.pig.EvalFunc;
import org.apache.pig.PigWarning;
import org.apache.pig.data.Tuple;

import java.io.IOException;

public class CaseConverter extends EvalFunc<String> {

    public String exec(Tuple tuple) throws IOException {
        try {
            String columnData = tuple.get(0).toString();
            String caseType = tuple.get(1).toString();
            if (columnData == null) {
                return null;
            } else {
                if (caseType.equalsIgnoreCase("UPPER")) {
                    return columnData.toUpperCase();
                } else if (caseType.equalsIgnoreCase("LOWER")) {
                    return columnData.toLowerCase();
                } else {
                    //title case
                    columnData = columnData.toLowerCase();
                    char charAtFirstPlace = columnData.charAt(0);
                    String firstCharToString = new String("" + charAtFirstPlace);
                    String firstCharString = firstCharToString.toUpperCase();
                    return firstCharString + columnData.substring(1);
                }
            }
        } catch (Exception e) {
            super.warn("Exception Generated ",PigWarning.UDF_WARNING_1);
            return null;
        }
    }
}
