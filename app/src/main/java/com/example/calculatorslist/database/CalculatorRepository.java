package com.example.calculatorslist.database;

import android.content.Context;
import android.util.Log;

import com.example.models.Calculator;

import java.util.List;

public class CalculatorRepository {

    private static final String TAG = "CalculatorRepository";
    private CalculatorDao calculatorDao;

    public CalculatorRepository(Context context){
        CalculatorRoomDatabase calculatorRoomDatabase = CalculatorRoomDatabase.getDatabase(context);
        calculatorDao = calculatorRoomDatabase.CalculatorDao();
    }

    public void insertCalculator(Calculator calculator){
        calculatorDao.insert(calculator);
    }

    public void updateCalculator(Calculator calculator){
        Log.d(TAG, "updateCalculator: " + getCalculators());
        calculatorDao.delete(calculator);
        Log.d(TAG, "updateCalculator: " + getCalculators());
        calculatorDao.insert(calculator);
        Log.d(TAG, "updateCalculator: " + getCalculators());
    }

    public List<Calculator> getCalculators(){
        return calculatorDao.getAll();
    }
}
