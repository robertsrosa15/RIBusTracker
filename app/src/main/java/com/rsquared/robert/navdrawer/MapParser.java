package com.rsquared.robert.navdrawer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

/**
 * Created by Robert on 9/3/2014.
 */
public class MapParser extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String url = this.getIntent().getExtras().getString(getString(R.string.url));
        setMapInfo(url);
    }

    public void setMapInfo(final String url) {
        final Thread thread = new Thread() {
            public void run() {
                try {
                    URLReaderMap urlReaderMap = new URLReaderMap();

                    ArrayList<String> mapInfoStringArrayList = urlReaderMap.getMapInfo(url);
                    String[] mapInfoStringArray = new String[mapInfoStringArrayList.size()];
                    mapInfoStringArray = mapInfoStringArrayList.toArray(mapInfoStringArray);

                    ArrayList<String> routePathArrayList = urlReaderMap.getRoutePath(url);
                    String[] routePathStringArray = new String[routePathArrayList.size()];
                    routePathStringArray = routePathArrayList.toArray(routePathStringArray);

                    Bundle bundle = new Bundle();
                    bundle.putStringArray(getString(R.string.array_map_info), mapInfoStringArray);
                    bundle.putStringArray(getString(R.string.array_route_path), routePathStringArray);
                    Intent intent = new Intent("android.intent.action.MAPSACTIVITY").putExtras(bundle);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
//                    finish();
                }
            }
        };
        thread.start();

    }
}