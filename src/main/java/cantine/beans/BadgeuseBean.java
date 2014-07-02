package cantine.beans;


import org.apache.commons.lang3.StringUtils;

public class BadgeuseBean {

    int nbTentativeConnexion;

    //Données valorisées
    String presenceAujourdhui;
    String resteAfaireAujourdhui;
    String tpsRecupereAujourdhui;
    String tpsTotalCummuleAujourdhui;
    String simulationDepart;

    boolean astuBadge = false;

    private String cleanDuration(String s) {
        String tmp="";
        if (s!=null) {
            tmp = s.replace("PT", "");
            if (tmp.startsWith("-")) {
                return "-" + StringUtils.remove(tmp, "-").toLowerCase();
            }
        }
        return tmp.toLowerCase();

    }

    public boolean isAstuBadge() {
        return astuBadge;
    }

    public void setAstuBadge(boolean astuBadge) {
        this.astuBadge = astuBadge;
    }

    public String getPresenceAujourdhui() {
        return cleanDuration(presenceAujourdhui);
    }

    public void setPresenceAujourdhui(String presenceAujourdhui) {
        this.presenceAujourdhui = cleanDuration(presenceAujourdhui);
    }

    public String getResteAfaireAujourdhui() {
        return resteAfaireAujourdhui;
    }

    public void setResteAfaireAujourdhui(String resteAfaireAujourdhui) {
        this.resteAfaireAujourdhui = cleanDuration(resteAfaireAujourdhui);
    }

    public String getTpsRecupereAujourdhui() {
        return tpsRecupereAujourdhui;
    }

    public void setTpsRecupereAujourdhui(String tpsRecupereAujourdhui) {
        this.tpsRecupereAujourdhui = cleanDuration(tpsRecupereAujourdhui);
    }

    public String getTpsTotalCummuleAujourdhui() {
        return tpsTotalCummuleAujourdhui;
    }

    public void setTpsTotalCummuleAujourdhui(String tpsTotalCummuleAujourdhui) {
        this.tpsTotalCummuleAujourdhui = cleanDuration(tpsTotalCummuleAujourdhui);
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
        this.simulationDepart = cleanDuration(simulationDepart);
    }


}
