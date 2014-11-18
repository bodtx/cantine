package cantine.beans;


import cantine.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

public class BadgeuseBean {

    int nbTentativeConnexion;

    //Données valorisées
    String presenceAujourdhui;
    String resteAfaireAujourdhui;
    String tpsRecupereAujourdhui;
    String tpsTotalCummuleAujourdhui;
    String simulationDepart;
    String pauseMidi;

    boolean astuBadge = false;

    List<BMouv> entreeSortieList = new ArrayList<BMouv>();




    public boolean isAstuBadge() {
        return astuBadge;
    }

    public void setAstuBadge(boolean astuBadge) {
        this.astuBadge = astuBadge;
    }

    public String getPresenceAujourdhui() {
        return DateUtils.cleanDuration(presenceAujourdhui);
    }

    public void setPresenceAujourdhui(String presenceAujourdhui) {
        this.presenceAujourdhui = DateUtils.cleanDuration(presenceAujourdhui);
    }

    public String getResteAfaireAujourdhui() {
        return resteAfaireAujourdhui;
    }

    public void setResteAfaireAujourdhui(String resteAfaireAujourdhui) {
        this.resteAfaireAujourdhui = DateUtils.cleanDuration(resteAfaireAujourdhui);
    }

    public String getTpsRecupereAujourdhui() {
        return tpsRecupereAujourdhui;
    }

    public void setTpsRecupereAujourdhui(String tpsRecupereAujourdhui) {
        this.tpsRecupereAujourdhui = DateUtils.cleanDuration(tpsRecupereAujourdhui);
    }

    public String getTpsTotalCummuleAujourdhui() {
        return tpsTotalCummuleAujourdhui;
    }

    public void setTpsTotalCummuleAujourdhui(String tpsTotalCummuleAujourdhui) {
        this.tpsTotalCummuleAujourdhui = DateUtils.cleanDuration(tpsTotalCummuleAujourdhui);
    }

    public int getNbTentativeConnexion() {
        return nbTentativeConnexion;
    }

    public void setNbTentativeConnexion(int nbTentativeConnexion) {
        this.nbTentativeConnexion = nbTentativeConnexion;
    }

    public String getSimulationDepart() {
        return simulationDepart;
    }

    public void setSimulationDepart(String simulationDepart) {
        this.simulationDepart = DateUtils.cleanDuration(simulationDepart);
    }

    public String getPauseMidi() {
        return DateUtils.cleanDuration(pauseMidi);
    }

    public void setPauseMidi(String pauseMidi) {
        this.pauseMidi = pauseMidi;
    }


    public void setEntreeSortieList(List<BMouv> entreeSortieList) {
        this.entreeSortieList = entreeSortieList;
    }

    public List<BMouv> getEntreeSortieList() {
        return entreeSortieList;
    }
}
