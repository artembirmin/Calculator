package com.example.data.repositories;

import com.example.data.db.CalculatorDao;
import com.example.data.db.CalculatorRoomDatabase;
import com.example.models.Calculator;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

public class CalculatorsListRepositoryImpl implements CalculatorsListRepository {

    private static final String TAG = "CalculatorRepository";
    private CalculatorDao dao;

    public CalculatorsListRepositoryImpl(CalculatorRoomDatabase calculatorRoomDatabase) {
        dao = calculatorRoomDatabase.CalculatorDao();
    }

    public void deleteAll() {
        dao.deleteAll();
    }

    @Override
    public Single<Calculator> getNewCalculator(String name) {
        return Single.just(new Calculator(name));
    }

    public void insertCalculator(Calculator calculator) {
        dao.insert(calculator);
    }

    public void updateCalculator(Calculator calculator) {
        dao.delete(calculator);
        dao.insert(calculator);
    }

    @Override
    public Single<Calculator> getCalculator(String id) {
        System.out.println(id);
        return dao.getById(id);
                //calculators.get(calculators.size() - position - 1);
    }

    public Single<List<Calculator>> getCalculators() {
        return dao.getAll();
    }
}
