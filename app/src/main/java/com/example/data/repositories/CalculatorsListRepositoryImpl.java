package com.example.data.repositories;

import com.example.data.db.CalculatorDao;
import com.example.data.db.CalculatorRoomDatabase;
import com.example.models.Calculator;

import java.util.Collections;
import java.util.List;

import io.reactivex.Single;

public class CalculatorsListRepositoryImpl implements CalculatorsListRepository {

    private static final String TAG = "CalculatorRepository";
    private final CalculatorDao dao;
    private static long count;

    public CalculatorsListRepositoryImpl(CalculatorRoomDatabase calculatorRoomDatabase) {
        dao = calculatorRoomDatabase.CalculatorDao();
        count = dao.getCount();
    }

    public void deleteAll() {
        dao.deleteAll();
        count = 0;
    }

    @Override
    public Single<Calculator> getNewCalculator(String name) {
        return Single.just(new Calculator(name));
    }

    @Override
    public Single<List<Calculator>> getFromBySize(long from, long size) {
        List<Calculator> list = dao.getFromBySize(count - from - size, size);
        Collections.reverse(list);
        return Single.just(list);
    }

    public void insertCalculator(Calculator calculator) {
        dao.insert(calculator);
        count++;
    }

    public void updateCalculator(Calculator calculator) {
        dao.delete(calculator);
        dao.insert(calculator);
    }

    @Override
    public Single<Calculator> getCalculator(String id) {
        return dao.getById(id);
                //calculators.get(calculators.size() - position - 1);
    }

    public Single<List<Calculator>> getCalculators() {
        return dao.getAll();
    }
}
