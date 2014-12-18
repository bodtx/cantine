package cantine.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public class DateUtils {

    static final DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String cleanDuration(String s) {
        String tmp = "";
        if (s != null) {
            tmp = s.replace("PT", "");
            if (tmp.startsWith("-")) {
                return "-" + StringUtils.remove(tmp, "-").toLowerCase();
            }
        }
        return tmp.toLowerCase();

    }


    public static Duration parseDuration(String s) {
        if (s.contains("-")) {
            s = s.replaceAll("-", "-PT");
        } else {
            s = "PT" + s;
        }
        s = s.replaceAll("\\.", "H");
        s = s + "M";
        return Duration.parse(s);

    }

    public static Date parseDate(String d){
        Date result = new Date();
        try {
            result = format.parse(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }
}
