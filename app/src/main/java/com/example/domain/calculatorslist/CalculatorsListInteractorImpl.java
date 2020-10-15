package com.example.domain.calculatorslist;

import android.util.Log;

import com.example.data.network.weather.WeatherApiProvider;
import com.example.data.network.weather.WeatherApiProviderImpl;
import com.example.data.repositories.CalculatorsListRepository;
import com.example.data.repositories.CalculatorsListRepositoryImpl;
import com.example.models.Calculator;
import com.example.models.CommonListItem;
import com.example.models.Weather;

import java.util.Collection;
import java.util.List;

public class CalculatorsListInteractorImpl implements CalculatorsListInteractor {

    private static final String TAG = "CalculatorsListInterato";
    CalculatorsListRepository calculatorsListRepository;
    WeatherApiProvider weatherApiProvider;

    public CalculatorsListInteractorImpl() {
        calculatorsListRepository = new CalculatorsListRepositoryImpl();
        weatherApiProvider = new WeatherApiProviderImpl();
    }

    @Override
    public Calculator getCalculator(int position) {
        return calculatorsListRepository.getCalculator(position);
    }

    @Override
    public void deleteAll() {
        calculatorsListRepository.deleteAll();
    }

    @Override
    public Collection<Calculator> getCalculators() {
        return calculatorsListRepository.getCalculators();
    }


    @Override
    public Calculator getNewCalculator(String name) {
        return calculatorsListRepository.getNewCalculator(name);
    }

    @Override
    public void addWeather(List<CommonListItem> items) {
        Weather weather = weatherApiProvider.getWeather();
        Log.d(TAG, "addWeather: " + weather);
        int len = items.size();
        for (int i = 0; i < len; i++) {
            if (i % 3 == 0)
                items.add(weather);
        }
        Log.d(TAG, "addWeather: " + items.toString());
    }
}
