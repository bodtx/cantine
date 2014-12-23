package cantine.db;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

@Entity
public class Setting implements Serializable {


    @Id
    @Column(nullable = false)
    private String cer;

    @Column(nullable = false)
    private String nom;

    public String getCer() {
        return cer;
    }

    public void setCer(String cer) {
        this.cer = cer;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((cer == null) ? 0 : cer.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Setting other = (Setting) obj;
        if (cer == null) {
            if (other.cer != null)
                return false;
        } else if (!cer.equals(other.cer))
            return false;
        return true;
    }

}
