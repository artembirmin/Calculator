package com.example.calculatorslist.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.models.Calculator;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Calculator.class}, version = 1, exportSchema = false)//Вместо последнего можно сделать так https://stackoverflow.com/a/44424908/1363731
public abstract class AppDatabase extends RoomDatabase {

    public abstract CalculatorDao CalculatorDao();

    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "database").allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
