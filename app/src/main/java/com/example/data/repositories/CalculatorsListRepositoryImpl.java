package com.example.data.repositories;

import android.util.Log;

import androidx.room.Dao;

import com.example.app.App;
import com.example.data.db.CalculatorDao;
import com.example.data.db.CalculatorRoomDatabase;
import com.example.domain.calculatorslist.CalculatorsListRepository;
import com.example.models.Calculator;

import java.util.List;

public class CalculatorsListRepositoryImpl implements CalculatorsListRepository {

    private static final String TAG = "CalculatorRepository";
    private CalculatorDao calculatorDao;

    public CalculatorsListRepositoryImpl(){
        CalculatorRoomDatabase calculatorRoomDatabase = App.getInstance().getDatabase();
        calculatorDao = calculatorRoomDatabase.CalculatorDao();
        Log.d(TAG, "CalculatorsListRepositoryImpl: ");
    }

    public void deleteAll(){
        calculatorDao.deleteAll();
    }

    public void insertCalculator(Calculator calculator){
        calculatorDao.insert(calculator);
        Log.d(TAG, "insertCalculator: " + getCalculators());
    }

    public void updateCalculator(Calculator calculator){
       // Log.d(TAG, "updateCalculator: " + getCalculators());
        calculatorDao.delete(calculator);
       // Log.d(TAG, "updateCalculator: " + getCalculators());
        calculatorDao.insert(calculator);
       // Log.d(TAG, "updateCalculator: " + getCalculators());
    }

    @Override
    public Calculator getCalculator(int position) {
        List<Calculator> calculators = calculatorDao.getAll();
        return calculators.get(calculators.size() - position - 1);
    }

    public List<Calculator> getCalculators(){
        return calculatorDao.getAll();
    }
}
