package com.example.data.repositories;

import com.example.models.Calculator;

import java.util.List;

public interface CalculatorsListRepository {

    void insertCalculator(Calculator calculator);

    void updateCalculator(Calculator calculator);

    Calculator getCalculator(int position);

    List<Calculator> getCalculators();

    void deleteAll();

    Calculator getNewCalculator(String name);
}
