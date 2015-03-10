package cantine.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Fayot implements Serializable {


    @Id
    @Column(nullable = false)
    private String cer;

    @Column
    private String credit;

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getCer() {
        return cer;
    }

    public void setCer(String cer) {
        this.cer = cer;
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
        Fayot other = (Fayot) obj;
        if (cer == null) {
            if (other.cer != null)
                return false;
        } else if (!cer.equals(other.cer))
            return false;
        return true;
    }

}
