package cantine.db;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Choix implements Serializable {

    private static final long serialVersionUID = 3369632266934915373L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String nom;

    @ElementCollection
    private List<String> plats;

    @Column(nullable = false)
    private Timestamp date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<String> getPlats() {
        return plats;
    }

    public void setPlats(List<String> plats) {
        this.plats = plats;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

}
