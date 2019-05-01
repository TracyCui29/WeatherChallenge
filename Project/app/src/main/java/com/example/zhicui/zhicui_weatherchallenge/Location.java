package com.example.zhicui.zhicui_weatherchallenge;

public class Location {

    String city;
    String temp;
    String weekday;

    public Location(String city, String temp, String weekday) {
        this.city = city;
        this.temp = temp;
        this.weekday = weekday;
    }

    public String getCity() {
        return city;
    }

    public String getTemp() {
        return temp;
    }

    public String getWeekday() {
        return weekday;
    }
}
