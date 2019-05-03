package com.example.zhicui.zhicui_weatherchallenge.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.zhicui.zhicui_weatherchallenge.MethodsKeys;
import com.example.zhicui.zhicui_weatherchallenge.R;
import com.example.zhicui.zhicui_weatherchallenge.Weather;

import java.util.ArrayList;
import java.util.Objects;

public class WeatherAdapter extends BaseAdapter {

    private final long BASE_ID = 0x01001;

    private final Context mContext;
    private final ArrayList<Weather> mWeathers;

    public WeatherAdapter(Context mContext, ArrayList<Weather> mWeathers) {
        this.mContext = mContext;
        this.mWeathers = mWeathers;
    }

    @Override
    public int getCount() {
        return mWeathers.size();
    }


    @Override
    public Object getItem(int position) {
        if(mWeathers != null && position >= 0 || position < Objects.requireNonNull(mWeathers).size()){
            return mWeathers.get(position);
        }
        return null;
    }


    @Override
    public long getItemId(int position) {
        return BASE_ID+position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_weather,parent,false);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }
        else {
            vh = (ViewHolder) convertView.getTag();
        }

        //Display data
        String minmax = MethodsKeys.reformatTemp(mWeathers.get(position).getTemp_min()) + "   " + MethodsKeys.reformatTemp(mWeathers.get(position).getTemp_max());
        String hum = mWeathers.get(position).getHumi() + "%";
        vh.vh_date.setText(mWeathers.get(position).getDat());
        vh.vh_minmax.setText(minmax);
        vh.vh_humi.setText(hum);
        vh.vh_wind.setText(mWeathers.get(position).getWind());
        vh.vh_rain.setText(mWeathers.get(position).getRain());


        return convertView;
    }


    static class ViewHolder{
        final TextView vh_date;
        final TextView vh_minmax;
        final TextView vh_humi;
        final TextView vh_wind;
        final TextView vh_rain;


        ViewHolder(View layout){
            vh_date = layout.findViewById(R.id.date_tv_ad_wea);
            vh_minmax = layout.findViewById(R.id.temMaxMin_tv_ad_wea);
            vh_humi = layout.findViewById(R.id.humidity_tv_ad_wea);
            vh_wind = layout.findViewById(R.id.wind_tv_ad_wea);
            vh_rain = layout.findViewById(R.id.rain_tv_ad_wea);
        }
    }
}
