package com.example.data.repositories;

import com.example.data.network.weather.WeatherApi;
import com.example.models.Weather;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Retrofit;

public class WeatherApiRepositoryImpl implements WeatherApiRepository {

    private static final String TAG = "WeatherApiProvider";
    private Retrofit retrofit;
    WeatherApi weatherApi;

    public WeatherApiRepositoryImpl(WeatherApi weatherApi) {
        this.weatherApi = weatherApi;
    }

    @Override
    public Observable<Weather> getWeather(double lat, double lon) {
        return weatherApi.getCurrentWeatherData("" + lat, "" + lon, WEATHER_API_KEY);
    }
}
