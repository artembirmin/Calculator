package com.example.app;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import com.example.data.db.CalculatorRoomDatabase;

public class App extends Application {

    public static App instance;

    private CalculatorRoomDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, CalculatorRoomDatabase.class, "database")
                .allowMainThreadQueries()
                .build();
    }

    public static App getInstance() {
        return instance;
    }

    public CalculatorRoomDatabase getDatabase() {
        return database;
    }
}
