package com.example.zhicui.zhicui_weatherchallenge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.zhicui.zhicui_weatherchallenge.Adapters.WeatherAdapter;
import com.example.zhicui.zhicui_weatherchallenge.Fragments.TodayFragment;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {


    ArrayList<Weather> weatherArrayList = new ArrayList<>();
    String cityName;
    ListView fiveDayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        fiveDayList = findViewById(R.id.list_forecast_detail);

        Intent in = getIntent();
        weatherArrayList = (ArrayList<Weather>) in.getSerializableExtra(MethodsKeys.EXTRA_WEATHERS);
        cityName = in.getStringExtra(MethodsKeys.EXTRA_CITY_NAME);

        if(weatherArrayList.size()>1){
            getSupportFragmentManager().beginTransaction().replace(R.id.today_frame_detail,TodayFragment.newInstance(weatherArrayList.get(0),cityName)).commit();

        }
        weatherArrayList.remove(0);
        WeatherAdapter wa = new WeatherAdapter(this,weatherArrayList);
        fiveDayList.setAdapter(wa);

    }
}
