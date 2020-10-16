package com.example.data.repositories;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.models.GetWeatherCallback;
import com.example.data.network.weather.WeatherApi;
import com.example.models.Weather;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherApiRepositoryImpl implements WeatherApiRepository {

    private static final String TAG = "WeatherApiProvider";
    Retrofit retrofit;


    public WeatherApiRepositoryImpl() {
        retrofit = new Retrofit.Builder()
                .baseUrl(WeatherApiRepository.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    @Override
    public void getWeather(double lat, double lon, @NonNull final GetWeatherCallback callback) {
        final WeatherApi weatherApi = retrofit.create(WeatherApi.class);
        final Call<Weather> weatherResponseCall = weatherApi.getCurrentWeatherData("" + lat, "" + lon, WeatherApiRepository.WEATHER_API_KEY);
        weatherResponseCall.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                callback.onSuccess(response.body());
                Log.d(TAG, "onResponse: " + response.message() + " " + response.body());
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
                callback.onError(t);
            }
        });
    }
}
