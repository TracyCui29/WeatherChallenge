package com.example.zhicui.zhicui_weatherchallenge;

import java.io.Serializable;

public class Weather implements Serializable {

    String temp;//
    String dat;//
    String temp_max;//
    String temp_min;//
    String city;//
    String weat;//
    String humi;//
    String rain;//
    String wind;//

    public Weather(String temp, String dat, String temp_max, String temp_min, String city, String weat, String humi, String rain, String wind) {
        this.temp = temp;
        this.dat = dat;
        this.temp_max = temp_max;
        this.temp_min = temp_min;
        this.city = city;
        this.weat = weat;
        this.humi = humi;
        this.rain = rain;
        this.wind = wind;
    }

    public String getTemp() {
        return temp;
    }

    public String getDat() {
        return dat;
    }

    public String getTemp_max() {
        return temp_max;
    }

    public String getTemp_min() {
        return temp_min;
    }

    public String getCity() {
        return city;
    }

    public String getWeat() {
        return weat;
    }

    public String getHumi() {
        return humi;
    }

    public String getRain() {
        return rain;
    }

    public String getWind() {
        return wind;
    }
}
