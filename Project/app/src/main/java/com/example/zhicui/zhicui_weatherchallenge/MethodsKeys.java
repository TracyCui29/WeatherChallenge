package com.example.zhicui.zhicui_weatherchallenge;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;

public class MethodsKeys {

    public static final String EXTRA_CITY = "EXTRA_CITY";
    public static final String EXTRA_CITY_NAME = "EXTRA_CITY_NAME";
    public static final String EXTRA_WEATHERS = "EXTRA_WEATHERS";
    public static final String FILENAME = "mapChallenge.txt";


    //check network
    public static boolean isConnected(Context _context){

        ConnectivityManager mgr = (ConnectivityManager)_context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if(mgr != null){
            NetworkInfo info = mgr.getActiveNetworkInfo();
            return info != null && info.isConnected();
        }

        return false;
    }

    //get JSON
    public static String getNetworkData(String _url){

        HttpURLConnection connection = null;
        InputStream is = null;
        String data = "";


        try{
            URL url = new URL(_url);
            connection = (HttpURLConnection)url.openConnection();
            connection.connect();
        }
        catch (Exception e){
            e.printStackTrace();
        }


        try {
            //get data
            is = Objects.requireNonNull(connection).getInputStream();
            if(is != null){
                data = org.apache.commons.io.IOUtils.toString(is, "UTF-8");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            if(connection != null){

                try {
                    Objects.requireNonNull(is).close();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
            Objects.requireNonNull(connection).disconnect();
        }
        return data;
    }


    //format date
    public static String reformatDate(String d){
        java.util.Date date = new java.util.Date(Long.decode(d)*1000L);
        SimpleDateFormat spf = new SimpleDateFormat("MMMM dd",Locale.US);
        String dateString = spf.format(date);
        return dateString;

    }


    //format tem
    public static String reformatTemp(String temp){

        String tempString;
        if(temp.contains(".")){
            tempString = temp.substring(0,temp.indexOf("."));
        }
        else {
            tempString = temp;
        }
        return tempString+ "°";

    }




}
