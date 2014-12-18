package cantine.service;

import cantine.beans.Depart;
import cantine.beans.Departs;
import cantine.beans.StationVelib;
import cantine.utils.DateUtils;
import cantine.utils.HTTPUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TisseoService {


    final String API_KEY = "ae93e25245ab9ba689e7461a6863b6c20";

    public Departs prochainsPassages(String login, String mdp) {
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("key", API_KEY));
        nvps.add(new BasicNameValuePair("operatorCode", "1801"));

        Document doc = HTTPUtils.executeHttp(login, mdp, "http://api.tisseo.fr/v1/stops_schedules.xml", nvps);

        Departs departs = new Departs();

        //ce qui suit est très moche :)
        Element stop = doc.child(0).child(0);
        String stopCode = stop.attributes().asList().get(1).getValue();
        String stopName = stop.attributes().asList().get(2).getValue();
        departs.setStopCode(stopCode);
        departs.setStopName(stopName);

        List<Depart> dep = new ArrayList<Depart>();
        Elements depertures = doc.select("departure");
        for (Element e : depertures) {
            Depart d = new Depart();
            d.setNumero(e.child(0).attributes().asList().get(1).getValue());
            d.setNomLigne(e.child(0).attributes().asList().get(0).getValue());
            d.setColor(e.child(0).attributes().asList().get(3).getValue());
            d.setDestination(e.child(1).attributes().asList().get(0).getValue());
            d.setDepart(DateUtils.parseDate(e.attributes().asList().get(0).getValue()));
            dep.add(d);
        }

        departs.setDeparts(dep);

        return departs;
    }

    public boolean problemeTisseo(String login, String mdp) {
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("key", API_KEY));
        nvps.add(new BasicNameValuePair("displayImportantOnly", "1"));

        Document doc = HTTPUtils.executeHttp(login, mdp, "http://api.tisseo.fr/v1/messages.xml", nvps);

        boolean problemTisseo =false;

        if(doc.child(0).childNodes().size()>1){
            problemTisseo=true;
        }
        return problemTisseo;
    }



    public List<StationVelib> velib(String login, String mdp) {
        List<StationVelib> result = new ArrayList<>();

        //Cambard
        Document doc = HTTPUtils.executeHttp(login, mdp, "http://www.velo.toulouse.fr/service/stationdetails/toulouse/258", null);
        StationVelib tmp = parseStationVelib(doc, "Cambard", "258");
        result.add(tmp);

        //Vasseur
        doc = HTTPUtils.executeHttp(login, mdp, "http://www.velo.toulouse.fr/service/stationdetails/toulouse/272", null);
        tmp = parseStationVelib(doc, "Vasseur", "272");
        result.add(tmp);

        //Argoulets
        doc = HTTPUtils.executeHttp(login, mdp, "http://www.velo.toulouse.fr/service/stationdetails/toulouse/215", null);
        tmp = parseStationVelib(doc, "Argoulets", "215");
        result.add(tmp);

        return result;

    }

    private StationVelib parseStationVelib(Document doc, String nomStation, String numeroStation) {
        StationVelib tmp = new StationVelib();

        //beurk
        String dispo = doc.child(0).child(0).childNode(0).attributes().asList().get(0).getValue();
        String libre = doc.child(0).child(1).childNode(0).attributes().asList().get(0).getValue();
        String total = doc.child(0).child(2).childNode(0).attributes().asList().get(0).getValue();

        tmp.setDisponible(dispo);
        tmp.setLibre(libre);
        tmp.setTotal(total);
        tmp.setNumero(numeroStation);
        tmp.setName(nomStation);

        return tmp;
    }

}
