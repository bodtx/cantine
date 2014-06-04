package cantine.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

public class BadgeuseReader {

    /**
     * @param args
     * @throws IOException
     * @throws ClientProtocolException
     */
    public void read() throws ClientProtocolException, IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://gtabadge.rh.recouv/webquartzacq/acq/badge.do");
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("USERID", "CER31xxxx"));
        nvps.add(new BasicNameValuePair("PASSWORD", "xxxxx"));
        nvps.add(new BasicNameValuePair("Cpts", "Afficher+compteurs"));
        nvps.add(new BasicNameValuePair("Connexion", "y"));
        nvps.add(new BasicNameValuePair("DECALHOR", "-120"));
        nvps.add(new BasicNameValuePair("TIME", "1401711007956"));
        httpPost.setEntity(new UrlEncodedFormEntity(nvps));
        CloseableHttpResponse response2 = httpclient.execute(httpPost);

        try {
            System.out.println(response2.getStatusLine());

            HttpEntity entity2 = response2.getEntity();
            final String body = EntityUtils.toString(entity2);
            System.out.println(body);

            String[][] trtd = null;
            Document doc = Jsoup.parse(body);
            Elements tables = doc.getElementsByClass("acqArray");
            // premier tableau de la badgeuse
            Elements trs = tables.get(0).select("tr");
            trtd = new String[trs.size()][];
            for (int i = 0; i < trs.size(); i++) {
                Elements tds = trs.get(i).select("td");

                trtd[i] = new String[tds.size()];
                for (int j = 0; j < tds.size(); j++) {
                    trtd[i][j] = tds.get(j).text();
                }
            }

            // TODO faire pareil avec les autres tableau et faire des calculs de fou
            EntityUtils.consume(entity2);
        } finally {
            response2.close();
        }

    }

}
