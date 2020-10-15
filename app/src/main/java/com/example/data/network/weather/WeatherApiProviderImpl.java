package com.example.data.network.weather;

import android.util.Log;

import com.example.models.Weather;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherApiProviderImpl implements WeatherApiProvider {

    private static final String TAG = "WeatherApiProvider";
    double lat = 45.03;
    double lon = 38.98;

    @Override
    public Weather getWeather() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WeatherApiProvider.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final WeatherApi weatherApi = retrofit.create(WeatherApi.class);
        final Call<Weather> weatherResponseCall = weatherApi.getCurrentWeatherData("" + lat, "" + lon, WeatherApiProvider.WEATHER_API_KEY);
        final Weather[] weather = new Weather[1];
        weatherResponseCall.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                weather[0] = response.body();
                Log.d(TAG, "onResponse: " + weather[0]);
                Log.d(TAG, "onResponse: " + response.message() + " " + response.body());
                return response.body();
            }
            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
        Log.d(TAG, "getWeather: " + weather[0]);
        return weather[0];
    }
}
