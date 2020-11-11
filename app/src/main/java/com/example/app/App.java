package com.example.app;

import android.app.Application;

import androidx.room.Room;

import com.example.data.db.CalculatorRoomDatabase;
import com.example.di.app.AppComponent;
import com.example.di.app.AppModule;
import com.example.di.app.DaggerAppComponent;

public class App extends Application {

    public static App instance;
    private AppComponent appComponent;
    private CalculatorRoomDatabase database;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        inject();
        database = Room.databaseBuilder(this, CalculatorRoomDatabase.class, "database")
                .allowMainThreadQueries()
                .build();
    }

    private void inject() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public CalculatorRoomDatabase getDatabase() {
        return database;
    }
}
