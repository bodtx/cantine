package cantine.utils;

import org.apache.commons.lang3.StringUtils;

import java.time.Duration;

public class DateUtils {

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
}
