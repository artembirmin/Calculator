package com.example.data.repositories;

import com.example.models.Weather;

import io.reactivex.Observable;
import io.reactivex.Single;

public interface WeatherApiRepository {

    String WEATHER_API_KEY = "52890ff4987cf82ef04c79cccc4bd74c";
    String BASE_URL = "https://api.openweathermap.org/";

    Single<Weather> getWeather(double lat, double lon);
}
