package com.example.domain.calculatorslist;

import com.example.models.Calculator;
import com.example.models.CommonListItem;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public interface CalculatorsListInteractor {

    Calculator getCalculator(int position);

    void deleteAll();

    Collection<Calculator> getCalculators();

    Calculator getNewCalculator(String name);

    void addWeather(List<CommonListItem> items);
}
