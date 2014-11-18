package cantine.beans;

import cantine.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.MINUTES;

public class Temptation {


    Duration dcJour;
    Duration dcCumuleVeille;
    Duration dcCumule;
    Duration dcPeriodeVeille;
    Duration dcPeriodique;
    List<BMouv> BMouvList = new ArrayList<BMouv>();
    Duration JOURNEEMIN = Duration.ofHours(7).plus(Duration.ofMinutes(48));
    Duration PAUSEMIN = Duration.ofMinutes(30);
    LocalTime DEBPAUSEMIDI = LocalTime.of(11, 00);
    LocalTime FINPAUSEMIDI = LocalTime.of(14, 00);

    public LocalTime getDEBPAUSEMIDI() {
        return DEBPAUSEMIDI;
    }

    public LocalTime getFINPAUSEMIDI() {
        return FINPAUSEMIDI;
    }

    public List<BMouv> getBMouvList() {
        return BMouvList;
    }

    public Duration getJOURNEEMIN() {
        return JOURNEEMIN;
    }

    public Duration getPAUSEMIN() {
        return PAUSEMIN;
    }

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

        dcJour = DateUtils.parseDuration(compteurs[1][1]);
        dcCumuleVeille = DateUtils.parseDuration(compteurs[2][1]);
        dcCumule = DateUtils.parseDuration(compteurs[3][1]);
        dcPeriodeVeille = DateUtils.parseDuration(compteurs[4][1]);
        dcPeriodique = DateUtils.parseDuration(compteurs[5][1]);
    }


    public Duration getPresenceBadgeJour() {
        Duration total = Duration.ZERO;
        for (BMouv bMouv : BMouvList) {
            total = total.plus(bMouv.getDuree());
        }

        Duration pause = getTempsPauseMidi();
        // si on n'a pas pris le minimum il faut corriger le temps travaillé
        if (pause != null && !PAUSEMIN.minus(pause).isNegative()) {
            total = total.minus(PAUSEMIN.minus(pause));
        }

        return total;
    }

    public Duration getTempsPauseMidi() {
        if (LocalTime.now().isBefore(FINPAUSEMIDI)) {
            return null;
        }
        // temps maximum pris dans la période optionnelle
        Duration tmpPause = Duration.of(DEBPAUSEMIDI.until(FINPAUSEMIDI, MINUTES), MINUTES);
        for (BMouv bMouv : BMouvList) {
            if (bMouv.getEntree().isAfter(DEBPAUSEMIDI) && bMouv.getEntree().isBefore(FINPAUSEMIDI)) {
                if (bMouv.getSortie() != null) {
                    if (bMouv.getSortie().isAfter(DEBPAUSEMIDI) && bMouv.getSortie().isBefore(FINPAUSEMIDI)) {
                        // le mouvement est entirement sur la zone de pause, on l'enlève du temps de pause
                        tmpPause = tmpPause.minus(bMouv.getDuree().toMinutes(), MINUTES);
                    } else {
                        tmpPause = tmpPause.minus(Duration.of(bMouv.getEntree().until(FINPAUSEMIDI, MINUTES), MINUTES));
                    }
                } else {
                    // on est sur le dernier mouvement donc la personne est encore en train de travailler
                    // on suppose qu'elle va travailler sur le reste du temps de pause disponible
                    tmpPause = tmpPause.minus(Duration.of(bMouv.getEntree().until(FINPAUSEMIDI, MINUTES), MINUTES));
                }
            } else {
                // entree n'est pas dans la pause
                if (bMouv.getSortie().isAfter(DEBPAUSEMIDI) && bMouv.getSortie().isBefore(FINPAUSEMIDI)) {
                    tmpPause = tmpPause.minus(Duration.of(DEBPAUSEMIDI.until(bMouv.getSortie(), MINUTES), MINUTES));
                }
            }
        }
        return tmpPause;
    }


    public Duration getResteAfaireAujourdhui() {
        return JOURNEEMIN.minus(getPresenceBadgeJour());
    }


}
