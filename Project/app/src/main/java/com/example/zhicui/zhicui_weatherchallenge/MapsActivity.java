package com.example.zhicui.zhicui_weatherchallenge;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = "MapsActivity";
    private GoogleMap mMap;
    Location currentLocation;
    LatLng latLng;
    ArrayList<City> cityArrayList = new ArrayList<>();
    FusedLocationProviderClient fusedLocationProviderClient;
    int REQUEST_LOCATION = 101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLastLocation();
    }


    private void fetchLastLocation(){

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            //with out permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION);

        }else {

            //with permission
            Task<Location> task = fusedLocationProviderClient.getLastLocation();
            task.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {

                    if(location != null){

                        currentLocation = location;
                        latLng = new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
                        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
                        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                                .findFragmentById(R.id.map);
                        Objects.requireNonNull(mapFragment).getMapAsync(MapsActivity.this);


                    }
                    else {
                        Toast.makeText(MapsActivity.this, "NO LOCATION", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        Log.i(TAG, "onMapReady: " + currentLocation.toString());

        //focus on current position
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,5));
        getCity(latLng);


        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                //each time with one pin
                mMap.clear();
                Log.i(TAG, "Lat: " + latLng.latitude  + "Long: " + latLng.longitude);
                // Setting the position for the marker
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                final City city = getCity(latLng);

                //0 - zip code
                //1 - city name
                if(city.city.contains("known")){
                    markerOptions.title("Unknown city, try other place");
                }
                else {
                    markerOptions.title("Click to store "+city.city + " weather");
                }
                mMap.addMarker(markerOptions);

                //set click action
                mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        Log.i(TAG, "onInfoWindowClick: 112233: " + city.city);
                        if(!city.city.contains("known")){

                            //Save and back to home page
                            Intent gotoHome = new Intent(MapsActivity.this, MainActivity.class);
                            gotoHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            gotoHome.putExtra(MethodsKeys.EXTRA_CITY,city);

                            //add new city in list
                            cityArrayList.clear();
                            GetSerializable();
                            cityArrayList.add(city);
                            SaveSerializable(cityArrayList);
                            //Save local
                            startActivity(gotoHome);
                        }
                    }
                });
            }
        });
    }

    public City getCity(LatLng latLng){

        Geocoder gcd = new Geocoder(MapsActivity.this, Locale.getDefault());
        String cit = "";

        try {

            List<Address> addresses = gcd.getFromLocation(latLng.latitude, latLng.longitude, 1);

            if (addresses.size() > 0){
                cit = addresses.get(0).getLocality();
            }
            else {
                cit = "unknown";
            }

        }
        catch (IOException e){
            e.printStackTrace();
            cit = "unknown";
        }

        if(cit==null||cit.equals("")){
            cit = "unknown";
        }


        return new City(cit,latLng.latitude,latLng.longitude);

    }

    //Save city
    private void SaveSerializable(ArrayList<City> _newsArrayList){

        try {
            FileOutputStream fos = openFileOutput(MethodsKeys.FILENAME, MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(_newsArrayList);
            oos.close();
            fos.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    //Get List of City
    private void GetSerializable(){
        cityArrayList.clear();
        try {

            FileInputStream fis = openFileInput(MethodsKeys.FILENAME);
            ObjectInputStream ois = new ObjectInputStream(fis);
            cityArrayList = (ArrayList<City>) ois.readObject();
            ois.close();
            fis.close();
        }
        catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }





}

