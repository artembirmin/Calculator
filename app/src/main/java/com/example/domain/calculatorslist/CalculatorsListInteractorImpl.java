package com.example.domain.calculatorslist;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.data.repositories.CalculatorsListRepository;
import com.example.data.repositories.WeatherApiRepository;
import com.example.models.Calculator;
import com.example.models.CommonListItem;
import com.example.models.Weather;

import java.util.List;


import io.reactivex.Observable;
import io.reactivex.Single;

public class CalculatorsListInteractorImpl implements CalculatorsListInteractor {

    private static final String TAG = "CalculatorsListInterato";
    CalculatorsListRepository calculatorsListRepository;
    WeatherApiRepository weatherApiRepository;

    public CalculatorsListInteractorImpl(CalculatorsListRepository calculatorsListRepository,
                                         WeatherApiRepository weatherApiRepository) {
        this.calculatorsListRepository = calculatorsListRepository;
        this.weatherApiRepository = weatherApiRepository;
    }

    @Override
    public Single<Calculator> getCalculator(int position, final List<CommonListItem> items) {
        Log.d(TAG, "getCalculator: " + position);
        return calculatorsListRepository.getCalculator(((Calculator) items.get(position)).getId());
    }

    @Override
    public String getIdByPosition(int position, final List<CommonListItem> items) {
        return ((Calculator) items.get(position)).getId();
    }

    @Override
    public void deleteAll() {
        calculatorsListRepository.deleteAll();
    }

    @Override
    public Single<List<Calculator>> getCalculators() {
        return calculatorsListRepository.getCalculators();
    }

    @Override
    public Single<Calculator> getNewCalculator(String name) {
        return calculatorsListRepository.getNewCalculator(name);
    }

    @Override
    public Observable<Weather> getWeather() {
        double lat = 45.03;
        double lon = 38.98;
        return weatherApiRepository.getWeather(lat, lon);
    }
}
