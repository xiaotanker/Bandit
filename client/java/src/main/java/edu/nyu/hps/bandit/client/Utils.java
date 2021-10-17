package edu.nyu.hps.bandit.client;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class Utils {
    private static String base ="http://localhost:8080";
    public static String sendPost(String url) throws IOException {

        String result = "";
        HttpPost post = new HttpPost(base + url);


        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(post)){

            result = EntityUtils.toString(response.getEntity());

            System.out.println(url);
            System.out.println(result);
            System.out.println(response.getStatusLine().getStatusCode());
        }

        return result;
    }
    public static String sendGet(String url) throws IOException {

        HttpGet request = new HttpGet(base + url);
        String result = "";
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(request)) {

            result = EntityUtils.toString(response.getEntity());
            System.out.println(url);
            System.out.println(result);
            System.out.println(response.getStatusLine().getStatusCode());
        }
        return result;
    }
}
