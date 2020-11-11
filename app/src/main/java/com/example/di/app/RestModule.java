package com.example.di.app;

import com.example.data.network.weather.WeatherApi;
import com.example.data.network.weather.WeatherNetworkService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RestModule {

    @Singleton
    @Provides
    Gson provideGson() {
        return new GsonBuilder().create();
    }

    @Singleton
    @Provides
    WeatherApi provideWeatherApi(WeatherNetworkService weatherNetworkService) {
        return weatherNetworkService.getWeatherApi();
    }
}
