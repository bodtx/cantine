package cantine.controller;

import org.apache.commons.lang3.StringUtils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Temptation {


    Duration dcJour;
    Duration dcCumuleVeille;
    Duration dcCumule;
    Duration dcPeriodeVeille;
    Duration dcPeriodique;
    List<BMouv> BMouvList = new ArrayList<BMouv>();

    public Duration getJOURNEEMIN() {
        return JOURNEEMIN;
    }

    Duration JOURNEEMIN = Duration.ofHours(7).plus(Duration.ofMinutes(48));

    public Duration getDcJour() {
        return dcJour;
    }

    public void setDcJour(Duration dcJour) {
        this.dcJour = dcJour;
    }

    public Duration getDcCumuleVeille() {
        return dcCumuleVeille;
    }

    public void setDcCumuleVeille(Duration dcCumuleVeille) {
        this.dcCumuleVeille = dcCumuleVeille;
    }

    public Duration getDcCumule() {
        return dcCumule;
    }

    public void setDcCumule(Duration dcCumule) {
        this.dcCumule = dcCumule;
    }

    public Duration getDcPeriodeVeille() {
        return dcPeriodeVeille;
    }

    public void setDcPeriodeVeille(Duration dcPeriodeVeille) {
        this.dcPeriodeVeille = dcPeriodeVeille;
    }

    public Duration getDcPeriodique() {
        return dcPeriodique;
    }

    public void setDcPeriodique(Duration dcPeriodique) {
        this.dcPeriodique = dcPeriodique;
    }


    public Temptation(String[][] mouvements, String[][] compteurs) {
        String entree = "";
        String sortie = "";

        for (String[] m : mouvements) {
            if (!StringUtils.isEmpty(m[1])) {
                entree = m[1];
            }
            if (!StringUtils.isEmpty(m[2])) {
                sortie = m[2];
            }

            if ((!StringUtils.isEmpty(entree)) && (!StringUtils.isEmpty(sortie))) {
                BMouv bm = new BMouv(entree, sortie);
                BMouvList.add(bm);
                entree = "";
                sortie = "";
            }
        }
        // dernier mouvement, pas de badge de sortie
        if (!StringUtils.isEmpty(entree)) {
            BMouv bm = new BMouv(entree, "");
            BMouvList.add(bm);
        }

        // on gère les compteurs

        dcJour = parseDuration(compteurs[1][1]);
        dcCumuleVeille = parseDuration(compteurs[2][1]);
        dcCumule = parseDuration(compteurs[3][1]);
        dcPeriodeVeille = parseDuration(compteurs[4][1]);
        dcPeriodique = parseDuration(compteurs[5][1]);
    }

    private Duration parseDuration(String s) {
        if (s.contains("-")) {
            s = s.replaceAll("-", "-PT");
        } else {
            s = "PT" + s;
        }
        s = s.replaceAll("\\.", "H");
        s = s + "M";
        return Duration.parse(s);

    }

    public Duration getPresenceBadgeJour() {
        Duration total = Duration.ZERO;
        for (BMouv bMouv : BMouvList) {
            total = total.plus(bMouv.getDuree());
        }
        return total;

    }

    public Duration getResteAfaireAujourdhui() {
        return JOURNEEMIN.minus(getPresenceBadgeJour());
    }


}
