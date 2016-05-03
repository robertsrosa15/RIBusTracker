package com.rsquared.robert.navdrawer;

import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.internal.ar;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import java.util.List;

public class MapsActivity extends FragmentActivity implements
        GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener{

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
//    LocationClient locationClient = null;
    private static final LatLng PAWTUCKET_TRANSIT = new LatLng(41.8769,-71.3838);
    private static final LatLng DEXTOR_BARTON = new LatLng(41.8808,-71.3902);
    private static final LatLng WEEDEN_LONSDALE = new LatLng(41.8766,-71.3997);
    private static LatLng MIDDLE_LOCATION = null;
    private static final LatLng POWER_ANDERTON = new LatLng(41.8726,-71.4191);
    private static final LatLng KENNEDY_PLAZA = new LatLng(41.8241,-71.4121);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
       /* locationClient = new LocationClient(this, this, this);
        locationClient.connect();*/
        setUpMapIfNeeded();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Connect the client.
       /* if(!locationClient.isConnected()) {
            locationClient.connect();
        }*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
        // Connect the client.
       /* if(!locationClient.isConnected()) {
            locationClient.connect();
        }*/
    }

    @Override
    protected void onStop() {
        /*if(locationClient.isConnected()) {
            locationClient.disconnect();
        }*/
        super.onStop();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();

/*            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(MIDDLE_LOCATION, 12);
            mMap.animateCamera(cameraUpdate);*/
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
       /* mMap.addMarker(new MarkerOptions().position(new LatLng(41.8769,-71.3838)).title("Pawtucket Transit Ctr.").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_bus_stop_blue)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(41.8808,-71.3902)).title("Dexter & Barton").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_bus_stop_blue)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(41.8766,-71.3997)).title("Weeden & Lonsdale").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_bus_stop_blue)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(41.8726,-71.4191)).title("Power & Anderton").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_bus_stop_blue)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(41.8241,-71.4121)).title("Kennedy Plaza").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_bus_stop_blue)));
        */mMap.setMyLocationEnabled(true);
/*
        Location location = locationClient.getLastLocation();
        double myLatitude = location.getLatitude();
        double myLongitude = location.getLongitude();
        mMap.addMarker(new MarkerOptions().position(new LatLng(myLatitude, myLongitude)).title("You"));

        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double myLatitude = location.getLatitude();
                double myLongitude = location.getLongitude();
                mMap.addMarker(new MarkerOptions().position(new LatLng(myLatitude, myLongitude)).title("You"));
            }
        };
*/
        String[] arrayRoutePath = this.getIntent().getExtras().getStringArray(getString(R.string.array_route_path));
        for(String routePath: arrayRoutePath) {
            PolylineOptions polylineOptions = new PolylineOptions().width(4).color(Color.BLUE);
            String route = getRouteString(routePath);
            String routeSplit = "";
            String[] routes = null;
            Log.i("route: ", route);
            List<LatLng> decodedLatLng = null;
//            if(route.length() > 300){
              /*  routeSplit = route.substring(150, 151);
                routes = route.split(routeSplit);
                try{
                    for(String routeSep : routes)
//                   String route1 = "xEp[jFv_@rBxNoIbCo@dC_AbDBlBt@nBzEBnDf@bEhBvHr";
                        decodedLatLng = PolyUtil.decode(routeSep);
                }catch (Exception e){
                    e.printStackTrace();
                }*/
//            }else {
//                    decodedLatLng = null;
            while(route.contains("\\\\")){
                route = cleanBackSlash(route);
            }

            try {
                decodedLatLng = PolyUtil.decode(route);
            } catch (Exception e) {
//                e.printStackTrace();
            }
//            }
            if (decodedLatLng != null) {

                Log.i("decodedLatLng: ", decodedLatLng.toString());
                for (LatLng latLng : decodedLatLng) {
                    polylineOptions.add(latLng);
                }
                if (decodedLatLng != null) {
                    mMap.addPolyline(polylineOptions);
                }
            }
        }
        String[] arrayMapInfo = this.getIntent().getExtras().getStringArray(getString(R.string.array_map_info));

//        int middleLatLng = Math.round(arrayMapInfo.length/2);
        double latitude = 0;
        double longitude = 0;
        int totalLatLng = 0;
        for(String mapInfo: arrayMapInfo){
            LatLng latLng = getMarkerLatLng(mapInfo);

            String markerName = getMarkerName(mapInfo);
            if(latLng != null){
              mMap.addMarker(new MarkerOptions().position(latLng).title(markerName).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_bus_stop_blue)));

              latitude = latitude + latLng.latitude;
              longitude = longitude - latLng.longitude;
              totalLatLng++;
            }
        }
        if(MIDDLE_LOCATION == null){
            MIDDLE_LOCATION = new LatLng(latitude/totalLatLng, -longitude/totalLatLng);
        }
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(MIDDLE_LOCATION, 12);
        mMap.animateCamera(cameraUpdate);

//        PolylineOptions polylineOptions = new PolylineOptions().add(PAWTUCKET_TRANSIT).add(DEXTOR_BARTON).add(WEEDEN_LONSDALE).add(POWER_ANDERTON).add(KENNEDY_PLAZA).width(3).color(Color.BLUE);
      /*  String route_path = "wbh~FznzrLZW_DoFsA}EmFpCmEdAoMzCwFfBqA^cA^qFGuBr@yBf@kBvA_DdFuEhG{JzMmKbCkOlDwQwWcKwF}VkEgA]wKwGoAY}J?qLlBiNdBeO~BoTrC_MjBpDxWuF`DmFtBcPDaPkIUcAy@oKAcA~ByPj@oDtA_YfAySHqMQmIsDn@}Id@aQ\\gGBcDTsCzAkCnBmLlB_CT{BaGCgASg@vAaCxFwFrLwLzMkQrDuDtHmKhFkHhAsAfB_BbDwB|@_@pBTUyBwBgCnAyB`@aA`@gC|@iBoBuA";
        List<LatLng> decodedLatLng = PolyUtil.decode(route_path);
        PolylineOptions polylineOptions = new PolylineOptions().width(3).color(Color.BLUE);
        for(LatLng latLng : decodedLatLng) {
           polylineOptions.add(latLng);
        }*/
//        mMap.addPolyline(polylineOptions);
    }

    private String cleanBackSlash(String route){
        return route.replace("\\\\", "\\");
    }

    private String getRouteString(String routePath){
        int startIndex = routePath.indexOf('"');
        routePath = routePath.substring(startIndex + 1);
        int endIndex = routePath.indexOf('"');
        routePath = routePath.substring(0, endIndex);
        return routePath;
    }
//    [["Dexter & Lonsdale",0,0,"ak1g","0","/real-time-bus-info?id=ak1g",""],

    private String getMarkerName(String markerName){
        int startIndex = markerName.indexOf('"');
        markerName = markerName.substring(startIndex + 1);
        int endIndex = markerName.indexOf('"');
        markerName = markerName.substring(0, endIndex);
        return markerName;
    }
    //    [["Dexter & Lonsdale",0,0,"ak1g","0","/real-time-bus-info?id=ak1g",""],

    private LatLng getMarkerLatLng(String markerLatLng){
/*        int startIndex = markerLatLng.indexOf(',');
        markerLatLng = markerLatLng.substring(startIndex + 1);
        int endIndex = markerLatLng.indexOf(',');
        markerLatLng = markerLatLng.substring(0, endIndex);*/
        double latitude = Double.parseDouble(getMarkerNumber(markerLatLng));

        int startIndex = markerLatLng.indexOf(',');
        markerLatLng = markerLatLng.substring(startIndex + 1);
        int secondIndex = markerLatLng.indexOf(',');
        markerLatLng = markerLatLng.substring(secondIndex + 1);
        int lastIndex = markerLatLng.indexOf(',');
        markerLatLng = markerLatLng.substring(0, lastIndex);
        double longitude = Double.parseDouble(markerLatLng);
        LatLng latLng = null;
        if(latitude != 0 && longitude != 0) {
          latLng = new LatLng(latitude,longitude);
        }
        return latLng;
    }

    private String getMarkerNumber(String latitude){
        int startIndex = latitude.indexOf(',');
        latitude = latitude.substring(startIndex + 1);
        int endIndex = latitude.indexOf(',');
        latitude = latitude.substring(0, endIndex);
        return latitude;
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onDisconnected() {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

//    var stops = [["Smithfield & Power Rd.",0,0,"bjnu","0","/real-time-bus-info?id=bjnu",""],
//            ["Dexter & Barton",41.8808,-71.3902,"deba","1","/real-time-bus-info?id=deba","Transfer Point 72,75"],
//            ["Dexter & West Hunt",0,0,"dewh","1","/real-time-bus-info?id=dewh","Transfer Point 72,75"],
//            ["Charles & Admiral",0,0,"itqq","0","/real-time-bus-info?id=itqq",""],
//            ["Kennedy Plaza",41.8241,-71.4121,"kepl","1","/real-time-bus-info?id=kepl",""],
//            ["Power Rd. & Anderton",0,0,"pd7j","0","/real-time-bus-info?id=pd7j",""],
//            ["Pawtucket Transit Ctr.",41.8769,-71.3838,"plf3","0","/real-time-bus-info?id=plf3",""],
//            ["Power & Anderton",41.8726,-71.4191,"poan","1","/real-time-bus-info?id=poan",""],
//            ["WalMart Providence",0,0,"walp","1","/real-time-bus-info?id=walp","Transfer Point on Charles St. 51,52,53,54,58,72"],
//            ["Weeden & Lonsdale",41.8766,-71.3997,"welo","1","/real-time-bus-info?id=welo",""]]
}
