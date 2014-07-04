package cantine.controller;

import org.apache.commons.lang3.StringUtils;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static java.time.temporal.ChronoUnit.MINUTES;

public class BMouv {

    private LocalTime entree;
    private LocalTime sortie;
    private Duration duree;

    public BMouv(String aEntree, String aSortie) {
        entree = LocalTime.parse(aEntree, DateTimeFormatter.ofPattern("H.mm"));
        if (!StringUtils.isEmpty(aSortie)) {
            sortie = LocalTime.parse(aSortie, DateTimeFormatter.ofPattern("H.mm"));
            duree = Duration.of(entree.until(sortie, MINUTES), MINUTES);
        } else {
            sortie = null;
            duree = null;
        }

    }

    public LocalTime getSortie() {
        return sortie;
    }

    public LocalTime getEntree() {
        return entree;
    }

    public Duration getDuree() {
        if (duree == null) {
            return Duration.of(entree.until(LocalTime.now(), MINUTES), MINUTES);
        }
        return duree;
    }


}
