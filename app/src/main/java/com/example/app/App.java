package com.example.app;

import android.app.Application;

import androidx.room.Room;

import com.example.data.db.CalculatorRoomDatabase;

public class App extends Application {

    public static App instance;

    private CalculatorRoomDatabase database;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, CalculatorRoomDatabase.class, "database")
                .allowMainThreadQueries()
                .build();
    }

    public CalculatorRoomDatabase getDatabase() {
        return database;
    }
}
