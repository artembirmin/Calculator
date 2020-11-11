package com.example.di.app;

import android.content.Context;

import com.example.data.db.CalculatorDBService;
import com.example.data.db.CalculatorRoomDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DBModule {

    @Singleton
    @Provides
    CalculatorRoomDatabase provideCalculatorRoomDatabase(Context context){
        return new CalculatorDBService(context).getCalculatorRoomDatabase();
    }
}
