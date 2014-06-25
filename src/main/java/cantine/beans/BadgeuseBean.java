package cantine.beans;


public class BadgeuseBean {
    
	int nbTentativeConnexion;
	
	//Données brutes
	String presenceBadgeJour;
    String DCJour;
    String DCCumuleVeille;
    String DCCumule;
    String DCPeriodeVeille;
    String DCPeriodique;
    String[][] mouvements;
    
    //Données valorisées
    String presenceAujourdhui;
    String resteAfaireAujourdhui;
    String tpsRecupereAujourdhui;
    String tpsTotalCummuleAujourdhui;
    String simulationDepart;

	public String getPresenceBadgeJour() {
		return presenceBadgeJour;
	}

	public void setPresenceBadgeJour(String presenceBadgeJour) {
		this.presenceBadgeJour = presenceBadgeJour;
	}

	public String getDCJour() {
		return DCJour;
	}

	public void setDCJour(String dCJour) {
		DCJour = dCJour;
	}


	public String getDCCumuleVeille() {
		return DCCumuleVeille;
	}

	public void setDCCumuleVeille(String dCCumuleVeille) {
		DCCumuleVeille = dCCumuleVeille;
	}

	public String getDCCumule() {
		return DCCumule;
	}

	public void setDCCumule(String dCCumule) {
		DCCumule = dCCumule;
	}

	public String getDCPeriodeVeille() {
		return DCPeriodeVeille;
	}

	public void setDCPeriodeVeille(String dCPeriodeVeille) {
		DCPeriodeVeille = dCPeriodeVeille;
	}

	public String getDCPeriodique() {
		return DCPeriodique;
	}

	public void setDCPeriodique(String dCPeriodique) {
		DCPeriodique = dCPeriodique;
	}

	public String[][] getMouvements() {
		return mouvements;
	}

	public void setMouvements(String[][] mouvements) {
		this.mouvements = mouvements;
	}

	public String getPresenceAujourdhui() {
		return presenceAujourdhui;
	}

	public void setPresenceAujourdhui(String presenceAujourdhui) {
		this.presenceAujourdhui = presenceAujourdhui;
	}

	public String getResteAfaireAujourdhui() {
		return resteAfaireAujourdhui;
	}

	public void setResteAfaireAujourdhui(String resteAfaireAujourdhui) {
		this.resteAfaireAujourdhui = resteAfaireAujourdhui;
	}

	public String getTpsRecupereAujourdhui() {
		return tpsRecupereAujourdhui;
	}

	public void setTpsRecupereAujourdhui(String tpsRecupereAujourdhui) {
		this.tpsRecupereAujourdhui = tpsRecupereAujourdhui;
	}

	public String getTpsTotalCummuleAujourdhui() {
		return tpsTotalCummuleAujourdhui;
	}

	public void setTpsTotalCummuleAujourdhui(String tpsTotalCummuleAujourdhui) {
		this.tpsTotalCummuleAujourdhui = tpsTotalCummuleAujourdhui;
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
		this.simulationDepart = simulationDepart;
	}


	
	
}
