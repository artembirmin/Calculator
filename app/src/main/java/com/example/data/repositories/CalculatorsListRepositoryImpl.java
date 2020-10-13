package com.example.data.repositories;

import com.example.app.App;
import com.example.data.db.CalculatorDao;
import com.example.data.db.CalculatorRoomDatabase;
import com.example.domain.calculatorslist.CalculatorsListRepository;
import com.example.models.Calculator;

import java.util.List;

public class CalculatorsListRepositoryImpl implements CalculatorsListRepository {

    private static final String TAG = "CalculatorRepository";
    private CalculatorDao dao;

    public CalculatorsListRepositoryImpl() {
        CalculatorRoomDatabase calculatorRoomDatabase = App.getInstance().getDatabase();
        dao = calculatorRoomDatabase.CalculatorDao();
    }

    public void deleteAll() {
        dao.deleteAll();
    }

    @Override
    public Calculator getNewCalculator(String name) {
        return new Calculator(name);
    }

    public void insertCalculator(Calculator calculator) {
        dao.insert(calculator);
    }

    public void updateCalculator(Calculator calculator) {
        dao.delete(calculator);
        dao.insert(calculator);
    }

    @Override
    public Calculator getCalculator(int position) {
        List<Calculator> calculators = dao.getAll();
        return calculators.get(calculators.size() - position - 1);
    }

    public List<Calculator> getCalculators() {
        return dao.getAll();
    }
}
