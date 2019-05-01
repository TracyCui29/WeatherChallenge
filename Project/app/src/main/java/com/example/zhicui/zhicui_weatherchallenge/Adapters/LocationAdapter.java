package com.example.zhicui.zhicui_weatherchallenge.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.zhicui.zhicui_weatherchallenge.Location;
import com.example.zhicui.zhicui_weatherchallenge.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class LocationAdapter extends BaseAdapter {

    //Adapter of home page adapter
    Context mContext;
    LocationClickListener mListener;
    ArrayList<Location> mLocations;
    long BASE_ID = 0x01001;

    //constructor
    public LocationAdapter(Context mContext, LocationClickListener mListener, ArrayList<Location> mLocations) {
        this.mContext = mContext;
        this.mListener = mListener;
        this.mLocations = mLocations;
    }

    public interface LocationClickListener{
        void locationClick();
        void deleteClick(Location location);
    }

    @Override
    public int getCount() {
        return mLocations.size();
    }

    @Override
    public Object getItem(int position) {
        if(mLocations != null && position >= 0 || position < mLocations.size()){
            return mLocations.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return BASE_ID + position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder vh;

        if(convertView != null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_location,parent,false);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        }
        else {
            vh = (ViewHolder) convertView.getTag();
        }
        //Set text
        vh.vh_city.setText(mLocations.get(position).getCity());
        vh.vh_temp.setText(mLocations.get(position).getTemp());

        vh.vh_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: remove the city from list - need pass something (String or object)
                mListener.deleteClick(mLocations.get(position));
            }
        });
        return convertView;
    }


    static class ViewHolder{
        TextView vh_city,vh_temp,vh_delete;

        ViewHolder(View layout){
            vh_city = layout.findViewById(R.id.city_text_ad);
            vh_temp = layout.findViewById(R.id.temp_text_ad);
            vh_delete = layout.findViewById(R.id.delete_text_ad);
        }
    }


}


