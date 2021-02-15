package com.example.di.activity;

import com.example.data.db.CalculatorRoomDatabase;
import com.example.data.network.weather.WeatherApi;
import com.example.data.repositories.CalculatorsListRepository;
import com.example.data.repositories.CalculatorsListRepositoryImpl;
import com.example.data.repositories.WeatherApiRepository;
import com.example.data.repositories.WeatherApiRepositoryImpl;
import com.example.di.activity.PerActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @PerActivity
    @Provides
    WeatherApiRepository provideWeatherApiRepository(WeatherApi weatherApi){
        return new WeatherApiRepositoryImpl(weatherApi);
    }

    @PerActivity
    @Provides
    CalculatorsListRepository provideCalculatorsListRepository(CalculatorRoomDatabase calculatorRoomDatabase){
        return new CalculatorsListRepositoryImpl(calculatorRoomDatabase);
    }
}
