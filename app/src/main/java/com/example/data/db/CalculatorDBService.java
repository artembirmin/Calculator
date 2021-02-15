package com.example.data.db;

import android.content.Context;

import androidx.room.Room;

import javax.inject.Inject;

public class CalculatorDBService implements CalculatorDBProvider {
    private CalculatorRoomDatabase calculatorRoomDatabase;

    @Inject
    public CalculatorDBService(Context context) {
        calculatorRoomDatabase = Room.databaseBuilder(context, CalculatorRoomDatabase.class, "database")
                .allowMainThreadQueries()
                .build();
    }

    @Override
    public CalculatorRoomDatabase getCalculatorRoomDatabase() {
        return calculatorRoomDatabase;
    }
}
