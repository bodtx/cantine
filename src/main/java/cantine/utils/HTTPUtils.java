package cantine.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;

public class HTTPUtils {

    private static CloseableHttpClient createHttpClientWithProxy() {
        HttpClientBuilder hcBuilder = HttpClients.custom();
        HttpHost proxy = new HttpHost("proxy2.cer31.recouv", 8000, "http");
        DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
        hcBuilder.setRoutePlanner(routePlanner);
        CloseableHttpClient httpClient = hcBuilder.build();
        return httpClient;
    }

    private static HttpClientContext createHttpContext(String login, String mdp) {
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(login, mdp));
        HttpClientContext localContext = HttpClientContext.create();
        localContext.setCredentialsProvider(credentialsProvider);
        return localContext;
    }


    public static Document executeHttp(String login, String mdp, String url, List<NameValuePair> nvps){
        CloseableHttpClient httpclient = createHttpClientWithProxy();
        HttpClientContext httpContext = createHttpContext(login, mdp);
        HttpPost httpPost = new HttpPost(url);
        CloseableHttpResponse response2 = null;
        Document doc = null;
        if(nvps!=null) {
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(nvps));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        try {
            response2 = httpclient.execute(httpPost, httpContext);
            HttpEntity entity2 = response2.getEntity();
            final String body = EntityUtils.toString(entity2);
            doc = Jsoup.parse(body, "", Parser.xmlParser());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            response2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return doc;
    }
}
