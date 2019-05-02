package com.example.zhicui.zhicui_weatherchallenge;

import android.content.IntentSender;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Date;
import java.util.ArrayList;

public class WeatherAsyncTask extends AsyncTask<String, String, ArrayList<Weather>> {

    Finished mFinishedInterface;
    ArrayList<Weather> weatherArrayList = new ArrayList<>();
    private static final String TAG = "WeatherAsyncTask";



    public WeatherAsyncTask(Finished mFinishedInterface) {
        this.mFinishedInterface = mFinishedInterface;
    }

    public interface Finished{
        void onFinish(ArrayList<Weather> weather);
    }


    @Override
    protected ArrayList<Weather> doInBackground(String... strings) {

        String web = strings[0];

        String jsonData = MethodsKeys.getNetworkData(web);

        try {

            String temp;//
            String date;//
            String temp_max;//
            String temp_min;//
            String city;//
            String weat;//
            String humi;//
            String rain;//
            String wind;//

            JSONObject l1 = new JSONObject(jsonData);

            //city name
            JSONObject city1 = l1.getJSONObject("city");
            city = city1.getString("name");

            JSONArray l2 = l1.getJSONArray("list");
            for (int i = 0; i < l2.length(); i++) {
                JSONObject l3 = l2.getJSONObject(i);
                date = l3.getString("dt");
                //main
                JSONObject main = l3.getJSONObject("main");
                temp = main.getString("temp");
                temp_max = main.getString("temp_max");
                temp_min = main.getString("temp_min");
                humi = main.getString("humidity");
                //weather
                JSONObject weather = l3.getJSONArray("weather").getJSONObject(0);
                weat = weather.getString("main");
                //wind
                JSONObject wind1 = l3.getJSONObject("wind");
                wind = wind1.getString("speed");
                //rain(?)
                try {
                    JSONObject rain1 = l3.getJSONObject("rain");
                    rain = rain1.getString("3h");
                }
                catch (Exception e){
                    //e.printStackTrace();
                    rain = "N/A";
                }


                //            String temp = "";//
                //            String date= "";//
                //            String temp_max= "";//
                //            String temp_min="";//
                //            String city="";//
                //            String weat="";//
                //            String humi="";//
                //            String rain = "";//
                //            String wind = "";//


                if (i==0|i==7|i==15|i==23|i==31|i==39){

                    java.util.Date d = new java.util.Date(Long.decode(date)*1000L);

                    Log.i(TAG, "--------------------------------------------");
                    Log.i(TAG, "doInBackground temp: " + temp);
                    Log.i(TAG, "doInBackground temp_max: " + temp_max);
                    Log.i(TAG, "doInBackground temp_min: " + temp_min);
                    Log.i(TAG, "doInBackground date: " + d);
                    Log.i(TAG, "doInBackground city: " + city);
                    Log.i(TAG, "doInBackground weat: " + weat);
                    Log.i(TAG, "doInBackground humi: " + humi);
                    Log.i(TAG, "doInBackground rain: " + rain);
                    Log.i(TAG, "doInBackground wind: " + wind);
                    Log.i(TAG, "--------------------------------------------");

                    Weather wea = new Weather(temp,date,temp_max,temp_min,city,weat,humi,rain,wind);
                    weatherArrayList.add(wea);
                }


            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return weatherArrayList;
    }


    @Override
    protected void onPostExecute(ArrayList<Weather> weathers) {
        super.onPostExecute(weathers);
        Log.i(TAG, "onPostExecute: " + weathers.size());
        if(mFinishedInterface!= null){
            mFinishedInterface.onFinish(weathers);
        }
    }
}
