package com.example.zhicui.zhicui_weatherchallenge;

import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Objects;

class WeatherAsyncTask extends AsyncTask<String, String, ArrayList<Weather>> {

    private final Finished mFinishedInterface;
    private final ArrayList<Weather> weatherArrayList = new ArrayList<>();
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

        //read json
        try {

            String temp;//
            String date;//
            String temp_max;//
            String temp_min;//
            String weat;//
            String humi;//
            String rain;//
            String wind;//

            JSONObject l1 = new JSONObject(jsonData);


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

                //add weather
                Weather wea = new Weather(MethodsKeys.reformatTemp(temp),MethodsKeys.reformatDate(date),temp_max,temp_min,weat,humi,rain,wind);
                weatherArrayList.add(wea);
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

        ArrayList<Weather> filiteredWeathers = new ArrayList<>();


        if(mFinishedInterface!= null){
            //calculate 5 days min and max temp
            for (int i = 0; i < weathers.size()-1; i++) {
                Weather weather1 = weathers.get(i);
                Weather weather2 = weathers.get(i+1);

                if(weather1.getDat().equals(weather2.getDat())){
                    if(Double.parseDouble(weather1.temp_min)<Double.parseDouble(weather2.temp_min)){
                        weather2.setTemp_min(weather1.temp_min);
                    }
                    else {
                        weather1.setTemp_min(weather2.temp_min);
                    }

                    if(Double.parseDouble(weather1.temp_max)>Double.parseDouble(weather2.temp_max)){
                        weather2.setTemp_max(weather1.temp_max);
                    }
                    else {
                        weather1.setTemp_max(weather2.temp_max);
                    }

                }
                else {
                    //make today newest temp data
                    if(weather1.getDat().equals(weathers.get(0).getDat())){
                        weather1.setTemp(weathers.get(0).getTemp());
                        weather1.setHumi(weathers.get(0).getHumi());
                        weather1.setRain(weathers.get(0).getRain());
                        weather1.setWind(weathers.get(0).getWind());
                        weather1.setWeat(weathers.get(0).getWeat());
                        filiteredWeathers.add(weather1);
                    }
                    else {
                        Weather we = new Weather(weather1.getTemp(),weather1.getDat(),weather1.getTemp_max(),weather1.getTemp_min(),weather1.getWeat(),weather1.getHumi(),weather1.getRain(),weather1.getWind());
                        filiteredWeathers.add(we);
                    }
                }
            }

        }

        //add last weather as last day
        Weather we = new Weather(weathers.get(39).getTemp(),weathers.get(39).getDat(),weathers.get(39).getTemp_max(),weathers.get(39).getTemp_min(),weathers.get(39).getWeat(),weathers.get(39).getHumi(),weathers.get(39).getRain(),weathers.get(39).getWind());
        filiteredWeathers.add(we);
        Objects.requireNonNull(mFinishedInterface).onFinish(filiteredWeathers);
    }
}
