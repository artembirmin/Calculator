package com.example.domain.calculatorslist;

import com.example.models.Calculator;

import java.util.List;

public interface CalculatorsListRepository {

    void insertCalculator(Calculator calculator);
    void updateCalculator(Calculator calculator);
    Calculator getCalculator(int position);
    List<Calculator> getCalculators();
    void deleteAll();
}
