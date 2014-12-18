package cantine.beans;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by CER3100444 on 17/12/2014.
 */
public class Depart {

    Date depart;

    String numero;

    String color;

    String nomLigne;

    String destination;

    public Date getDepart() {
        return depart;
    }

    public void setDepart(Date depart) {
        this.depart = depart;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    //méhode utilisée dans le front
    public String getHeureDepart(){
        SimpleDateFormat formater = new SimpleDateFormat("HH:mm");
        return formater.format(depart);
    }

    public String getNomLigne() {
        return nomLigne;
    }

    public void setNomLigne(String nomLigne) {
        this.nomLigne = nomLigne;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
