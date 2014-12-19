package cantine.utils;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class DateUtils {


    static DateTimeFormatter parseFormat = new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd HH:mm:ss").toFormatter();

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

    public static String cleanDuration2(Duration d) {
        PeriodFormatter formatter = new PeriodFormatterBuilder()
                .appendMinutes()
                .toFormatter();
        String formatted = formatter.print(new Period(d));
        return formatted;
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



    public static LocalTime parseDate(String d){
        LocalTime localTime = LocalTime.parse(d, parseFormat);
        return localTime;
    }
}
