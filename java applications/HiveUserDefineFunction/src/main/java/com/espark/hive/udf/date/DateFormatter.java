package com.espark.hive.udf.date;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.*;

import java.text.SimpleDateFormat;

@Description(
        name="DateFormatter",
        value="returns a formatted data as per input format",
        extended=" SELECT DateFormatter(<data_column>) FROM <table>;"
)
public class DateFormatter extends UDF{
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("dd-MMM-yyyy");
    public Text evaluate(String dataToFormat, String inputDataFormat) {
        try {
            if (dataToFormat == null) {
                return null;
            }else{
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat(inputDataFormat);
                return new Text(SIMPLE_DATE_FORMAT.format(simpleDateFormat.parse(dataToFormat)));
            }
        }catch (Exception e){
            return null;
        }
    }

   /* public static void main(String[] args) {
        String dataToFormat ="2013/10/15";
        String inputDataFormat="yyyy/MM/dd";
        System.out.println("==> "+new DateFormatter().evaluate(dataToFormat,inputDataFormat));
    }*/
}
