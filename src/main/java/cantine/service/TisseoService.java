package cantine.service;

import cantine.beans.Depart;
import cantine.beans.Departs;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TisseoService {


    final String API_KEY = "ae93e25245ab9ba689e7461a6863b6c20";

    public Departs prochainsPassages(String login, String mdp) throws ParseException {
        CloseableHttpClient httpclient = createHttpClientWithProxy();
        HttpClientContext httpContext = createHttpContext(login, mdp);
        HttpPost httpPost = new HttpPost("http://api.tisseo.fr/v1/stops_schedules.xml");
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("key", API_KEY));
        nvps.add(new BasicNameValuePair("operatorCode", "1801"));
        CloseableHttpResponse response2 = null;

        Departs departs = new Departs();

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        try {
            response2 = httpclient.execute(httpPost, httpContext);
            HttpEntity entity2 = response2.getEntity();
            final String body = EntityUtils.toString(entity2);
            Document doc = Jsoup.parse(body, "",Parser.xmlParser());

            //ce qui suit est très moche :)
            //stop
            Element stop = doc.child(0).child(0);
            String stopCode = stop.attributes().asList().get(1).getValue();
            String stopName = stop.attributes().asList().get(2).getValue();

            departs.setStopCode(stopCode);
            departs.setStopName(stopName);

            List<Depart> dep = new ArrayList<>();
            //departures
            Elements depertures = doc.select("departure");
            for (Element e : depertures) {
                Depart d = new Depart();
                d.setNumero(e.child(0).attributes().asList().get(1).getValue());
                d.setNomLigne(e.child(0).attributes().asList().get(0).getValue());
                d.setColor(e.child(0).attributes().asList().get(3).getValue());
                d.setDestination(e.child(1).attributes().asList().get(0).getValue());
                d.setDepart(format.parse(e.attributes().asList().get(0).getValue()));
                dep.add(d);
            }

            departs.setDeparts(dep);

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            response2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return departs;

    }


    private CloseableHttpClient createHttpClientWithProxy() {
        HttpClientBuilder hcBuilder = HttpClients.custom();
        HttpHost proxy = new HttpHost("proxy2.cer31.recouv", 8000, "http");
        DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
        hcBuilder.setRoutePlanner(routePlanner);
        CloseableHttpClient httpClient = hcBuilder.build();
        return httpClient;
    }

    private HttpClientContext createHttpContext(String login, String mdp) {
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(login, mdp));
        HttpClientContext localContext = HttpClientContext.create();
        localContext.setCredentialsProvider(credentialsProvider);
        return localContext;
    }

}
