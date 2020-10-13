package com.example.domain.calculatorslist;

import com.example.models.Calculator;

import java.util.Collection;

public interface CalculatorsListInteractor {

    Calculator getCalculator(int position);

    void deleteAll();

    Collection<Calculator> getCalculators();

    Calculator getNewCalculator(String name);
}
