package com.rsquared.robert.navdrawer;

import android.app.Activity;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class NavDrawer extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private String theURL =  "http://www.ripta.com/1";
    private WebView myWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        // To provide a WebView in your own Activity, include a <WebView> in your layout,
        // or set the entire Activity window as a WebView during onCreate():
        myWebView = (WebView) findViewById(R.id.webview);/*        myWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.equalsIgnoreCase("http://m.ripta.com/mobile/map.php?id=1")) {
                    view.loadUrl(url);
//            Toast.makeText(view.getParent(), "Invalid Link", Toast.LENGTH_SHORT).show();
                    System.out.println("http://m.ripta.com/mobile/map.php?id=1");
                } else if (url.equalsIgnoreCase("http://m.ripta.com/schedules---maps-1")) {
                    System.out.println("http://m.ripta.com/schedules---maps-1");
                } else {
                    view.loadUrl(url);
                }
                return true;
            }
            });*/

//        myWebView.setWebViewClient(new RSquaredWebViewClient());
        myWebView.setWebViewClient(new RSquaredWebViewClient());

    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        String[] arrayTitle = this.getIntent().getExtras().getStringArray(getString(R.string.array_bus_title));
        String[] splitStrings = arrayTitle[number].split(" - ");
        String firstString = modifyFirstString(splitStrings[0].trim());
        String url = getString(R.string.url_ripta) + firstString;
        Bundle bundle = new Bundle();
        bundle.putString(getString(R.string.url), url);
        getIntent().putExtras(bundle);
        myWebView.loadUrl(url);
        mTitle = modifyTitleString(arrayTitle[number]);
//        startActivity(new Intent(this, MapParser.class));
    }

    private String modifyFirstString(String firstString){
        if(firstString.contains("x")){
            if(firstString.contains("61")) {
                firstString = firstString.replace("x", "-1");
            }else{
                firstString = firstString.replace("x", "");
            }
        }
        if(firstString.contains("X")){
            firstString = firstString.replace("X", "");
        }
        if(firstString.equalsIgnoreCase("R/Line")){
            firstString = firstString.replace("R/Line", "11-1");
        }
        return firstString;
    }

    private String modifyTitleString(String firstString){
        if(firstString.contains("amp;")){
            firstString = firstString.replace("amp;", "");
        }
        return firstString;
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.nav_drawer, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.4
/*        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }*/
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_nav_drawer, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((NavDrawer) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER) - 1);
        }
    }
}