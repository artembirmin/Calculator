package com.example.data.repositories;

import com.example.models.Calculator;

import java.util.List;

import io.reactivex.Single;

public interface CalculatorsListRepository {

    void insertCalculator(Calculator calculator);

    void updateCalculator(Calculator calculator);

    Single<Calculator> getCalculator(String id);

    Single<List<Calculator>> getCalculators();

    void deleteAll();

    Single<Calculator> getNewCalculator(String name);

    Single<List<Calculator>> getFromBySize(long from, long size);
}
