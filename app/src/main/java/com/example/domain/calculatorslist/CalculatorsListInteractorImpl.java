package com.example.domain.calculatorslist;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.models.GetWeatherCallback;
import com.example.data.repositories.CalculatorsListRepository;
import com.example.data.repositories.CalculatorsListRepositoryImpl;
import com.example.data.repositories.WeatherApiRepository;
import com.example.data.repositories.WeatherApiRepositoryImpl;
import com.example.models.Calculator;
import com.example.models.CommonListItem;
import com.example.models.Weather;
import com.example.presentation.calculatorslist.CalculatorsListPresenter;

import java.util.Collection;
import java.util.List;

public class CalculatorsListInteractorImpl implements CalculatorsListInteractor {

    private static final String TAG = "CalculatorsListInterato";
    CalculatorsListPresenter calculatorsListPresenter;
    CalculatorsListRepository calculatorsListRepository;
    WeatherApiRepository weatherApiRepository;

    public CalculatorsListInteractorImpl(CalculatorsListPresenter calculatorsListPresenter) {
        this.calculatorsListPresenter = calculatorsListPresenter;
        calculatorsListRepository = new CalculatorsListRepositoryImpl();
        weatherApiRepository = new WeatherApiRepositoryImpl();
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
    public void addWeather(final List<CommonListItem> items) {
        double lat = 45.03;
        double lon = 38.98;
        weatherApiRepository.getWeather(lat, lon, new GetWeatherCallback() {
            @Override
            public void onSuccess(Weather weather) {
                items.add(0, weather);
                calculatorsListPresenter.updateItems();
            }

            @Override
            public void onError(Throwable throwable) {

            }
        });

    }
}
