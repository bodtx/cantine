package cantine.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Plat implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 20959505325353612L;

    @Id
    private String nom;

    @Column
    private boolean accompagnement;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public boolean isAccompagnement() {
        return accompagnement;
    }

    public void setAccompagnement(boolean accompagnement) {
        this.accompagnement = accompagnement;
    }

}
