package com.example;

import android.app.Application;

import com.example.data.db.CalculatorRoomDatabase;
import com.example.di.app.AppComponent;
import com.example.di.app.AppModule;
import com.example.di.app.DaggerAppComponent;

public class App extends Application {

    public static App instance;
    private AppComponent appComponent;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        inject();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    private void inject() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }
}
