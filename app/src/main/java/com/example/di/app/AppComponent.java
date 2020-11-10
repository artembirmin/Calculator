package com.example.di.app;

import android.content.Context;

import com.example.data.network.weather.WeatherApi;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {AppModule.class, DBModule.class, RestModule.class})
@Singleton
public interface AppComponent {

    Context getContext();

  //  WeatherApi provideWeatherApi();
}
