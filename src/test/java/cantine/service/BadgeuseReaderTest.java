package cantine.service;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static java.time.temporal.ChronoUnit.MINUTES;

public class BadgeuseReaderTest {

    private String[][] initMouvements() {
        String[][] mouvements = new String[3][];
        mouvements[0] = new String[3];
        mouvements[0][0] = "1";
        mouvements[0][1] = "8.45";
        mouvements[0][2] = "";

        mouvements[1] = new String[3];
        mouvements[1][0] = "2";
        mouvements[1][1] = "";
        mouvements[1][2] = "12.04";

        mouvements[2] = new String[3];
        mouvements[2][0] = "3";
        mouvements[2][1] = "12.39";
        mouvements[2][2] = "";
        return mouvements;
    }

    @Test
    public void testTransform() throws Exception {

        LocalTime tmp1 = LocalTime.parse("8.45", DateTimeFormatter.ofPattern("H.mm"));
        LocalTime tmp2 = LocalTime.parse("12.04", DateTimeFormatter.ofPattern("H.mm"));
        Long lt = tmp1.until(tmp2, MINUTES);

        System.out.println(lt);

        Duration recup = Duration.of(lt, MINUTES);
        System.out.println(recup);

        String cpt = "-7.47";

        cpt = cpt.replaceAll("-","-PT");
        cpt = cpt.replaceAll("\\.","H");
        cpt = cpt + "M";

        Duration dur = Duration.parse(cpt);

        System.out.println(dur);

        String test = "-1H-43M";

        String sign = "";
        if (test.startsWith("-")) {
            sign = "-";
        }

        test = sign + StringUtils.remove(test,"-").toLowerCase();

        System.out.println(test);

    }
}