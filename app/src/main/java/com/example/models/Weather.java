package com.example.models;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Weather implements CommonListItem {

    @SerializedName("name")
    private String name;

    @SerializedName("weather")
    private ArrayList<WeatherItem> weather = new ArrayList<WeatherItem>();

    @SerializedName("main")
    private Main main;

    public String getName() {
        return name;
    }

    public ArrayList<WeatherItem> getWeather() {
        return weather;
    }

    @NonNull
    @Override
    public String toString() {
        return name + " " + weather.get(0).main + " " + String.format("%.1f", (main.temp - 273.15));
    }

    static class Main {

        @SerializedName("temp")
        public float temp;

    }

    static class WeatherItem {

        @SerializedName("main")
        private String main;

        public String getMain() {
            return main;
        }
    }
}

