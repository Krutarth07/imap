package com.krutarth07.development.imap;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class geomap extends AppCompatActivity implements View.OnClickListener {

    double longi, lati;
    SharedPreferences sharedPreferences;
    SharedPreferences prefsname;

    int locationCount = 0;
    private GoogleMap googleMap;
    private GoogleMap.OnMyLocationChangeListener myLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
        @Override
        public void onMyLocationChange(Location location) {

            lati = location.getLatitude();
            longi = location.getLongitude();

        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geomap);

        prefsname = getSharedPreferences("names",MODE_PRIVATE);
        TextView nor, sat, hyb;
        Button mark;

        nor = (TextView) findViewById(R.id.textView);
        sat = (TextView) findViewById(R.id.textView2);
        hyb = (TextView) findViewById(R.id.textView3);
        mark = (Button) findViewById(R.id.mark);

        nor.setOnClickListener(this);
        sat.setOnClickListener(this);
        hyb.setOnClickListener(this);
        mark.setOnClickListener(this);


        try {
            // Loading map
            initilizeMap();


            googleMap.setMyLocationEnabled(true);

            googleMap.getUiSettings().setMyLocationButtonEnabled(true);

            googleMap.getUiSettings().setCompassEnabled(true);

            googleMap.getUiSettings().setZoomControlsEnabled(true);

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }

            LatLng coordinate = new LatLng(21.2370015, 72.8532095);
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(coordinate));
            googleMap.getUiSettings().setRotateGesturesEnabled(true);


            sharedPreferences = getSharedPreferences("location", 0);
            locationCount = sharedPreferences.getInt("locationCount", 0);

            // If locations are already saved
            if (locationCount != 0) {

                String lat = "";
                String lng = "";

                // Iterating through all the locations stored
                for (int i = 0; i < locationCount; i++) {

                    // Getting the latitude of the i-th location
                    lat = sharedPreferences.getString("lat" + i, "0");

                    // Getting the longitude of the i-th location
                    lng = sharedPreferences.getString("lng" + i, "0");


                    // Drawing marker on the map
                    // latitude and longitude
                    drawMarker(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng)));
                }
            }
            googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {

                @Override
                public void onMapLongClick(LatLng point) {
                    locationCount++;

                    // Drawing marker on the map
                    drawMarker(point);

                    /** Opening the editor object to write data to sharedPreferences */
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    // Storing the latitude for the i-th location
                    editor.putString("lat" + Integer.toString((locationCount - 1)), Double.toString(point.latitude));

                    // Storing the longitude for the i-th location
                    editor.putString("lng" + Integer.toString((locationCount - 1)), Double.toString(point.longitude));

                    // Storing the count of locations or marker count
                    editor.putInt("locationCount", locationCount);

                    editor.commit();

                    Toast.makeText(getBaseContext(), "Marker is added to the Map", Toast.LENGTH_SHORT).show();

                }
            });


            googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @Override
                public View getInfoWindow(Marker marker) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {

                    View v = getLayoutInflater().inflate(R.layout.infowin, null);
                    LatLng latLng = marker.getPosition();

// Getting reference to the TextView to set latitude
                    TextView tvLat = (TextView) v.findViewById(R.id.textView4);

// Getting reference to the TextView to set longitude
                    TextView tvLng = (TextView) v.findViewById(R.id.textView5);

                    TextView ti = (TextView) v.findViewById(R.id.title);

                    TextView des = (TextView) v.findViewById(R.id.desc);

                    String title,desc;

                    title=prefsname.getString("name","");
                    desc=prefsname.getString("desc","");

                    ti.setText(title);

                    des.setText(desc);

// Setting the latitude
                    tvLat.setText("Latitude:" + latLng.latitude);

// Setting the longitude
                    tvLng.setText("Longitude:" + latLng.longitude);
                    return v;
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * function to load map. If map is not created it will create it for you
     */
    private void initilizeMap() {
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.map)).getMap();

            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initilizeMap();

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.textView:

                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;


            case R.id.textView2:

                googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;


            case R.id.textView3:

                googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;

            case R.id.mark:

            {

                // Removing the marker and circle from the Google Map
                googleMap.clear();

                // Opening the editor object to delete data from sharedPreferences
                SharedPreferences.Editor editor =  getSharedPreferences("location", 0).edit();
                SharedPreferences.Editor editor2 =  getSharedPreferences("names", 0).edit();
                // Clearing the editor
                editor.clear();
                editor2.clear();

                editor.commit();
                editor2.commit();
                // Setting locationCount to zero
                locationCount = 0;

                break;

            }


            default:
                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
        }

    }

    private void drawMarker(LatLng point) {

        SharedPreferences prefs = getSharedPreferences("names", MODE_PRIVATE);


        // Creating an instance of MarkerOptions
        MarkerOptions markerOptions = new MarkerOptions();

        markerOptions.position(point).title(prefs.getString("name", ""));

        Marker mar = googleMap.addMarker(markerOptions);
        // Adding marker on the Google Map

        mar.showInfoWindow();
    }


}

