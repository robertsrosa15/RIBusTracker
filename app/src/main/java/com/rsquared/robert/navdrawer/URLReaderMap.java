package com.rsquared.robert.navdrawer;

/**
 * Created by Robert on 8/29/2014.
 */

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class URLReaderMap {
    public static InputStream getInputStreamFromUrl(String url) {
        InputStream content = null;
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(new HttpGet(url));
            content = response.getEntity().getContent();
        } catch (Exception e) {
            e.printStackTrace();        }
        return content;
    }

    public ArrayList<String> getMapInfo(String theURL) throws IOException {
        ArrayList<String> stringArrayList = new ArrayList<String>();
        InputStream inputStream = getInputStreamFromUrl(theURL);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String inputLine;
        while ((inputLine = bufferedReader.readLine()) != null) {
            Log.i("inputeLine1: ", inputLine);
            if (inputLine.contains("var stops")) {
                stringArrayList.add(inputLine);
                while ((inputLine = bufferedReader.readLine()) != null) {
                    stringArrayList.add(inputLine);
                    if(inputLine.contains("]]")){
                        break;
                    }
                }
            }
        }
        return stringArrayList;

    }

    public ArrayList<String> getRoutePath(String theURL) throws IOException {
        ArrayList<String> routePathArrayList = new ArrayList<String>();
        InputStream inputStream = getInputStreamFromUrl(theURL);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String inputLine;
        while ((inputLine = bufferedReader.readLine()) != null) {
            Log.i("inputeLine: ", inputLine);
            if(inputLine.contains("var route_path")){
                routePathArrayList.add(inputLine);
            }
        }
        return routePathArrayList;
    }
}