package com.example.data.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.models.Calculator;

@Database(entities = {Calculator.class}, version = 1, exportSchema = false)//Вместо последнего можно сделать так https://stackoverflow.com/a/44424908/1363731
public abstract class CalculatorRoomDatabase extends RoomDatabase {

    public abstract CalculatorDao CalculatorDao();

}
