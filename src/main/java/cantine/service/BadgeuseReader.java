package cantine.service;

import cantine.beans.BadgeuseBean;
import cantine.controller.Temptation;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class BadgeuseReader {


    Temptation bms = null;

    public BadgeuseBean read(String login, String mdp) throws ClientProtocolException, IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(
                "http://gtabadge.rh.recouv/webquartzacq/acq/badge.do");
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("USERID", login));
        nvps.add(new BasicNameValuePair("PASSWORD", mdp));
        nvps.add(new BasicNameValuePair("Cpts", "Afficher+compteurs"));
        nvps.add(new BasicNameValuePair("Connexion", "y"));
        nvps.add(new BasicNameValuePair("DECALHOR", "-120"));
        nvps.add(new BasicNameValuePair("TIME", "1401711007956"));
        CloseableHttpResponse response2 = null;
        httpPost.setEntity(new UrlEncodedFormEntity(nvps));

        BadgeuseBean b = new BadgeuseBean();

        boolean connexionok = false;

        int nbTentative = 0;


        while (!connexionok && nbTentative < 100) {
            response2 = httpclient.execute(httpPost);

            // System.out.println(response2.getStatusLine());

            HttpEntity entity2 = response2.getEntity();
            final String body = EntityUtils.toString(entity2);
            // System.out.println(body);


            String[][] trtd = null;
            Document doc = Jsoup.parse(body);
            Elements tables = doc.getElementsByClass("acqArray");
            if (tables.size() > 0) {
                connexionok = true;
                // premier tableau de la controllers
                Elements trs = tables.get(0).select("tr");
                trtd = new String[trs.size()][];
                for (int i = 0; i < trs.size(); i++) {
                    Elements tds = trs.get(i).select("td");

                    trtd[i] = new String[tds.size()];
                    for (int j = 0; j < tds.size(); j++) {
                        trtd[i][j] = tds.get(j).text();
                    }
                }

                // si le nombre de mouvements est pair, il manque un badge d'entrée
                if(trtd==null || trtd.length==0 || trtd.length%2 !=0){
                    b.setAstuBadge(true);
                }

                // 2eme tableau de la controllers
                Elements trs2 = tables.get(2).select("tr");
                String[][] trtd2 = null;
                trtd2 = new String[trs2.size()][];
                for (int i = 0; i < trs2.size(); i++) {
                    Elements tds2 = trs2.get(i).select("td");

                    trtd2[i] = new String[tds2.size()];
                    for (int j = 0; j < tds2.size(); j++) {
                        trtd2[i][j] = tds2.get(j).text();
                    }
                }
                bms = new Temptation(trtd, trtd2);

                EntityUtils.consume(entity2);
            } else {
                nbTentative++;
            }

        }
        response2.close();
        b.setNbTentativeConnexion(nbTentative);
        return b;
    }

    public BadgeuseBean getBadgeInfos(String login, String mdp) {
        BadgeuseBean b = completeBadgeInfos(login,mdp);

        //traitement badge Infos
        updateBeanWithcalcul(b);

        return b;
    }

    /**
     * Vérifie si l'utilisateur a bien badgé
     * @param login
     * @param mdp
     * @return
     */
    public BadgeuseBean completeBadgeInfos(String login, String mdp) {
        BadgeuseBean b = null;
        try {
            b = read(login, mdp);
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return b;
    }

    public void updateBeanWithcalcul(BadgeuseBean b) {

        b.setPresenceAujourdhui(bms.getPresenceBadgeJour().toString());
        if (bms.getTempsPauseMidi() != null) {
            b.setPauseMidi(bms.getTempsPauseMidi().toString());
        }

        if (bms.getResteAfaireAujourdhui().isNegative()) {
            // si j'ai fait plus 7h48
            //tps récupéré Aujourd'hui : presence - 7h48
            b.setTpsRecupereAujourdhui(bms.getResteAfaireAujourdhui().abs().toString());
            //tmp recup veille plus temps fait aujourd'hui
            b.setTpsTotalCummuleAujourdhui(bms.getDcCumuleVeille().toString());
        } else {
            // si j'ai fait moins de 7h48
            //reste à faire aujourd'hui : 7h48 - presence
            b.setResteAfaireAujourdhui(bms.getResteAfaireAujourdhui().toString());
            //tps total cummulé aujourd'hui : tps cummulé de la veille
            b.setTpsTotalCummuleAujourdhui(bms.getDcCumuleVeille().toString());
            //simulation départ :  la presence + tps cummulé veille - 7h48
            b.setSimulationDepart(bms.getDcCumuleVeille().plus(bms.getPresenceBadgeJour()).minus(bms.getJOURNEEMIN()).toString());
            b.setTpsRecupereAujourdhui("");
        }
    }




}
