package com.rsquared.robert.navdrawer;

import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

/**
 * Created by Robert on 8/27/2014.
 */
public class RSquaredWebViewClient extends WebViewClient {

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if(url.equalsIgnoreCase("http://m.ripta.com/mobile/map.php?id=1")){
            System.out.println("http://m.ripta.com/mobile/map.php?id=1");
        }else if(url.equalsIgnoreCase("http://m.ripta.com/schedules---maps-1")) {
            System.out.println("http://m.ripta.com/schedules---maps-1");
        }else{
            view.loadUrl(url);
        }
        return true;
    }
}
