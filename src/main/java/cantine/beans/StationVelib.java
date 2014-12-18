package cantine.beans;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by CER3100444 on 17/12/2014.
 */
public class StationVelib {


    String numero;

    String name;

    String disponible;

    String libre;

    String total;


    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getDisponible() {
        return disponible;
    }

    public void setDisponible(String disponible) {
        this.disponible = disponible;
    }

    public String getLibre() {
        return libre;
    }

    public void setLibre(String libre) {
        this.libre = libre;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
