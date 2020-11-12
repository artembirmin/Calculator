package com.example.domain.calculatorslist;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.data.repositories.CalculatorsListRepository;
import com.example.data.repositories.WeatherApiRepository;
import com.example.models.Calculator;
import com.example.models.CommonListItem;
import com.example.models.Weather;
import com.example.presentation.calculatorslist.CalculatorsListPresenter;

import java.util.Collection;
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Calculator getCalculator(int position, final List<CommonListItem> items) {
        for (int i = 0; i < items.size() && i < position; i++) {
            if (items.get(i) instanceof Weather)
                position--;
        }
        Log.d(TAG, "getCalculator: " + position);
        return calculatorsListRepository.getCalculator(position);
    }


    @Override
    public void deleteAll() {
        calculatorsListRepository.deleteAll();
    }

    @Override
    public int getRealPosition(int position, List<CommonListItem> items) {
        for (int i = 0; i < items.size() && i < position; i++) {
            if (items.get(i) instanceof Weather)
                position--;
        }
        return position;
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
    public Observable<Weather> addWeather() {
        double lat = 45.03;
        double lon = 38.98;
        return weatherApiRepository.getWeather(lat, lon);
    }
}
