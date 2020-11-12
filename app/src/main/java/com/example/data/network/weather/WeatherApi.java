package com.example.data.network.weather;

import com.example.models.Weather;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {
    @GET("data/2.5/weather?")
    Observable<Weather> getCurrentWeatherData(@Query("lat") String lat,
                                              @Query("lon") String lon,
                                              @Query("APPID") String app_id);
}
