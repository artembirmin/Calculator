package com.example.data.repositories;

import androidx.annotation.NonNull;

import com.example.models.GetWeatherCallback;

public interface WeatherApiRepository {

    String WEATHER_API_KEY = "52890ff4987cf82ef04c79cccc4bd74c";
    String BASE_URL = "https://api.openweathermap.org/";

    void getWeather(double lat, double lon, @NonNull GetWeatherCallback callback);
}
