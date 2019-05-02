package com.example.zhicui.zhicui_weatherchallenge;

import java.io.Serializable;

public class City implements Serializable {

    String city;
    Double lat;
    Double lon;

    public City(String city, Double lat, Double lon) {
        this.city = city;
        this.lat = lat;
        this.lon = lon;
    }

    public String getCity() {
        return city;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLon() {
        return lon;
    }
}
