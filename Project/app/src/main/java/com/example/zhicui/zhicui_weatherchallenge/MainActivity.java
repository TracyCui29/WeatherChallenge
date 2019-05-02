package com.example.zhicui.zhicui_weatherchallenge;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhicui.zhicui_weatherchallenge.Adapters.LocationAdapter;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LocationAdapter.LocationClickListener,WeatherAsyncTask.Finished {

    private static final String TAG = "MainActivity";

    ListView location_lv;
    TextView noData_tv;
    //for read data
    ArrayList<City> cityArrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        location_lv = findViewById(R.id.list_location_main);
        noData_tv = findViewById(R.id.text_nodata_main);
        GetSerializable();
        Log.i(TAG, "onCreate: " + cityArrayList.size());

        reloadList();
    }


    //MENU

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mif = getMenuInflater();
        mif.inflate(R.menu.add_menu,menu);
        return true;
    }

    //MENU
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //ADD ACTION
        if(item.getItemId() == R.id.add_btn_menu){
            //TODO: goto map activity
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "We need permission of access location in order to use google map.", Toast.LENGTH_SHORT).show();
                //with out permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        101);
            }else {
                Intent gotoMap = new Intent(this,MapsActivity.class);
                startActivity(gotoMap);
            }
        }
        return super.onOptionsItemSelected(item);
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


    @Override
    public void locationClick(City city) {

        WeatherAsyncTask asy = new WeatherAsyncTask(this);
        String web = "http://api.openweathermap.org/data/2.5/forecast?&lat="+city.lat+"&lon="+city.lon+"&&units=imperial&APPID=6d88e52ea278672765eaf276b4bc39c4";
        asy.execute(web);
    }

    @Override
    public void deleteClick(City location) {
        Toast.makeText(this, location.city, Toast.LENGTH_SHORT).show();
        cityArrayList.remove(location);
        SaveSerializable(cityArrayList);
        reloadList();

    }

    public void reloadList(){
        //Check empty list and give feedback
        if(cityArrayList.size()>0){
            withData();
            LocationAdapter la = new LocationAdapter(this,this,cityArrayList);
            location_lv.setAdapter(la);
        }
        else {
            noData();
        }

    }

    public void withData(){
        location_lv.setVisibility(View.VISIBLE);
        noData_tv.setVisibility(View.INVISIBLE);
    }

    public void noData(){
        location_lv.setVisibility(View.INVISIBLE);
        noData_tv.setVisibility(View.VISIBLE);
    }


    @Override
    public void onFinish(ArrayList<Weather> weather) {
        //after download goto detail
        Intent gotoDetail = new Intent(this, DetailActivity.class);
        gotoDetail.putExtra(MethodsKeys.EXTRA_CITY,weather);
        startActivity(gotoDetail);
    }
}
