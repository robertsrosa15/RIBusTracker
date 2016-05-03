package com.rsquared.robert.navdrawer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

/**
 * Created by Robert on 9/3/2014.
 */
public class LogoActivity extends Activity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        setContentView(R.layout.logo);

        Thread thread = new Thread(){
            public void run() {
                try {
                    URLReaderLogo urlReaderLogo = new URLReaderLogo();
                    ArrayList<String> stringArrayList = urlReaderLogo.getBusTitle(getString(R.string.url_ripta));
                    String[] stringArray = new String[stringArrayList.size()];
                    stringArray = stringArrayList.toArray(stringArray);
                    Bundle bundle = new Bundle();
                    bundle.putStringArray(getString(R.string.array_bus_title), stringArray);
                    sleep(20);
                    Intent intent = new Intent("android.intent.action.NAVDRAWER").putExtras(bundle);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                finally {
                    finish();
                }
            }
        };
        thread.start();

        super.onCreate(savedInstanceState);
    }
}