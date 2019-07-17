package com.espark.hive.udf.string;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;

@Description(
        name = "CaseConverter",
        value = "returns a upper/lower/Title  case version of the input string.",
        extended = "select ExampleUDF(deviceplatform) from hivesampletable limit 10;"
)
public class CaseConverter extends UDF {

    public String evaluate(String columnData, String caseType) {
        try {
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
                    char charAtFirstPlace =  columnData.charAt(0);
                    String firstCharToString = new String("" + charAtFirstPlace);
                    String firstCharString = firstCharToString.toUpperCase();
                    return firstCharString + columnData.substring(1);
                }
            }
        } catch (Exception e) {
            return null;
        }
    }
}
