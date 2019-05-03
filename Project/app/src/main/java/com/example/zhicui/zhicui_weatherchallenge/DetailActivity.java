package com.example.zhicui.zhicui_weatherchallenge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.zhicui.zhicui_weatherchallenge.Adapters.WeatherAdapter;
import com.example.zhicui.zhicui_weatherchallenge.Fragments.TodayFragment;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {


    private ArrayList<Weather> weatherArrayList = new ArrayList<>();
    private String cityName;
    private ListView fiveDayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setTitle("");

        fiveDayList = findViewById(R.id.list_forecast_detail);
        fiveDayList.setSelector(android.R.color.transparent);

        //get weathers
        Intent in = getIntent();
        weatherArrayList = (ArrayList<Weather>) in.getSerializableExtra(MethodsKeys.EXTRA_WEATHERS);
        cityName = in.getStringExtra(MethodsKeys.EXTRA_CITY_NAME);

        //check num of data
        if(weatherArrayList.size()>1){
            getSupportFragmentManager().beginTransaction().replace(R.id.today_frame_detail,TodayFragment.newInstance(weatherArrayList.get(0),cityName)).commit();

        }
        //first weather is today, no need display again in listview
        weatherArrayList.remove(0);
        WeatherAdapter wa = new WeatherAdapter(this,weatherArrayList);
        fiveDayList.setAdapter(wa);

    }
}
