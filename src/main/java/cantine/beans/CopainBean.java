package cantine.beans;

public class CopainBean {
    private String nom;
    private Boolean inscrit;

    public CopainBean(String nom, Boolean inscrit) {
        this.nom = nom;
        this.inscrit = inscrit;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setInscrit(Boolean inscrit) {
        this.inscrit = inscrit;
    }

    public String getNom() {
        return nom;
    }

    public Boolean getInscrit() {
        return inscrit;
    }
}
