package com.example.domain.calculatorslist;

import com.example.models.Calculator;
import com.example.models.CommonListItem;
import com.example.models.Weather;

import java.util.List;


import io.reactivex.Observable;
import io.reactivex.Single;

public interface CalculatorsListInteractor {

    Single<Calculator> getCalculator(int position, List<CommonListItem> items);

    String getIdByPosition(int position, List<CommonListItem> items);

    void deleteAll();

    Single<List<Calculator>> getCalculators();

    Single<Calculator> getNewCalculator(String name);

    Single<Weather> getWeather();
}
