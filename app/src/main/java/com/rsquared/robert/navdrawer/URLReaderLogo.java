package com.rsquared.robert.navdrawer;

/**
 * Created by Robert on 8/29/2014.
 */
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.FormElement;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.net.*;
import java.io.*;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class URLReaderLogo {
    public ArrayList<String> getBusTitle(String theURL) {
        ArrayList<String> stringArrayList = new ArrayList<String>();
        try {
            Document doc = Jsoup.connect("http://www.ripta.com/schedules-maps").get();
            Elements elements = doc.select("span");
            int i = 0;
            for (Element element : elements) {
                boolean breakOut = false;
                if(element.hasClass("routenumber")) {
                    String elementHTML = element.html();
                    if(elementHTML.toString().equalsIgnoreCase("95x")){
                        breakOut = true;
                    }
                    element.append(" - " + element.parentNode().childNode(2).childNode(0));
                    String stringRoute = element.html();
                    if(stringRoute.contains("amp;")){
                        stringRoute = stringRoute.replace("amp;", "");
                    }
                    stringArrayList.add(stringRoute);
                    i += 2;
                }
                if(breakOut){
                    break;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return stringArrayList;

    }
}