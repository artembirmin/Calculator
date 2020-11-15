package com.example.di.app;

import android.content.Context;

import com.example.data.db.CalculatorRoomDatabase;
import com.example.data.network.weather.WeatherApi;
import com.example.presentation.routers.CommonCalculatorRouter;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {AppModule.class, DBModule.class, RestModule.class, NavigationModule.class})
@Singleton
public interface AppComponent {

    Context getContext();

    WeatherApi provideWeatherApi();

    CalculatorRoomDatabase provideCalculatorRoomDatabase();

    CommonCalculatorRouter provideCommonCalculatorRouter();
}
