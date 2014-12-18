package cantine.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CER3100444 on 17/12/2014.
 */
public class Departs {

    String stopCode ="";

    String stopName ="";

    List<Depart> departs = new ArrayList<>();

    public String getStopCode() {
        return stopCode;
    }

    public void setStopCode(String stopCode) {
        this.stopCode = stopCode;
    }

    public String getStopName() {
        return stopName;
    }

    public void setStopName(String stopName) {
        this.stopName = stopName;
    }

    public List<Depart> getDeparts() {
        return departs;
    }

    public void setDeparts(List<Depart> departs) {
        this.departs = departs;
    }
}
