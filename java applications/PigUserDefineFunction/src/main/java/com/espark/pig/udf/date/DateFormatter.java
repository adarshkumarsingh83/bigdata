package com.espark.pig.udf.date;

import org.apache.pig.EvalFunc;
import org.apache.pig.PigWarning;
import org.apache.pig.data.Tuple;

import java.io.IOException;
import java.text.SimpleDateFormat;

//  inputDataFormat=> yyyy/MM/dd
public class DateFormatter extends EvalFunc<String> {
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("dd-MMM-yyyy");

    public String exec(Tuple tuple) throws IOException {
        try {
            String dataToFormat = tuple.get(0).toString();
            String inputDataFormat = tuple.get(1).toString();
            if (dataToFormat == null) {
                return null;
            } else {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(inputDataFormat);
                return SIMPLE_DATE_FORMAT.format(simpleDateFormat.parse(dataToFormat));
            }
        } catch (Exception e) {
            super.warn("Exception Generated ", PigWarning.UDF_WARNING_1);
            return null;
        }
    }
}
