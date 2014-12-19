package cantine.beans;

import cantine.utils.DateUtils;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

/**
 * Created by CER3100444 on 17/12/2014.
 */
public class Depart {

    LocalTime depart;

    String numero;

    String color;

    String nomLigne;

    String destination;

    public LocalTime getDepart() {
        return depart;
    }

    public String getHeureDepart() {
        return depart.toString();
    }

    public void setDepart(LocalTime depart) {
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


    public String getDecompteDepart() {
        return DateUtils.cleanDuration2(Duration.between(LocalTime.now(), depart));
    }

}
