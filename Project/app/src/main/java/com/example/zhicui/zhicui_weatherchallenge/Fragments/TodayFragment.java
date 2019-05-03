package com.example.zhicui.zhicui_weatherchallenge.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zhicui.zhicui_weatherchallenge.MethodsKeys;
import com.example.zhicui.zhicui_weatherchallenge.R;
import com.example.zhicui.zhicui_weatherchallenge.Weather;

import java.util.Objects;


public class TodayFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private TextView date_today;
    private TextView tempMaxMin;
    private TextView temp_today;
    private TextView city;
    private TextView weather;
    private TextView humidity;
    private TextView wind;
    private TextView rain;
    private Weather weatherInfo;
    private String cityname;
    public TodayFragment() {
    }



    public static TodayFragment newInstance(Weather weather,String cityname) {
        TodayFragment fragment = new TodayFragment();
        Bundle args = new Bundle();

        args.putSerializable(ARG_PARAM1, weather);
        args.putString(ARG_PARAM2,cityname);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_today, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        if(getActivity()!=null){
            date_today = getActivity().findViewById(R.id.date_tv_today);
            tempMaxMin = getActivity().findViewById(R.id.temMaxMin_today);
            temp_today = getActivity().findViewById(R.id.temp_tv_today);
            city = getActivity().findViewById(R.id.city_tv_today);
            weather = getActivity().findViewById(R.id.weather_tv_today);
            humidity = getActivity().findViewById(R.id.humidity_tv_today);
            wind = getActivity().findViewById(R.id.wind_tv_today);
            rain = getActivity().findViewById(R.id.rain_tv_today);

            cityname = Objects.requireNonNull(getArguments()).getString(ARG_PARAM2);
            weatherInfo = (Weather)getArguments().getSerializable(ARG_PARAM1);

            //Display today weather
            if(weatherInfo!=null){

                String maxmin = MethodsKeys.reformatTemp(weatherInfo.getTemp_min())+ "   " + MethodsKeys.reformatTemp(weatherInfo.getTemp_max());
                String hum = weatherInfo.getHumi() + "%";
                date_today.setText(weatherInfo.getDat());
                tempMaxMin.setText(maxmin);
                temp_today.setText(weatherInfo.getTemp());
                city.setText(cityname);
                weather.setText(weatherInfo.getWeat());
                humidity.setText(hum);
                wind.setText(weatherInfo.getWind());
                rain.setText(weatherInfo.getRain());
            }


        }
    }


}
