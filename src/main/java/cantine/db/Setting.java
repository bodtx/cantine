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

    @Column
    private Boolean tisseo = false;

    @Column
    private Boolean asTubadge = false;

    @Column
    private Boolean cafe = false;

    @Column
    private Boolean psNext = false;

    @Column
    private Boolean cantine = false;

    @Column
    private Integer cafeJour=0;

    @Column
    private String email;

    public Integer getCafeJour() {
        return cafeJour;
    }

    public void setCafeJour(Integer cafeJour) {
        this.cafeJour = cafeJour;
    }

    public Boolean getTisseo() {
        return tisseo;
    }

    public void setTisseo(Boolean tisseo) {
        this.tisseo = tisseo;
    }

    public Boolean getAsTubadge() {
        return asTubadge;
    }

    public void setAsTubadge(Boolean asTubadge) {
        this.asTubadge = asTubadge;
    }

    public Boolean getPsNext() {
        return psNext;
    }

    public void setPsNext(Boolean psNext) {
        this.psNext = psNext;
    }

    public Boolean getCafe() {
        return cafe;
    }

    public void setCafe(Boolean cafe) {
        this.cafe = cafe;
    }

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

    public Boolean getCantine() {
        return cantine;
    }

    public void setCantine(Boolean cantine) {
        this.cantine = cantine;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
