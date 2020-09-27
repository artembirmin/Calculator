package com.example.calculatorslist.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.models.Calculator;

import java.util.LinkedList;
import java.util.List;

@Dao
public interface CalculatorDao {
    @Query("SELECT * FROM calculator")
    List<Calculator> getAll();

    @Query("SELECT * FROM calculator WHERE id = :id")
    Calculator getById(long id);

    @Insert
    void insert(Calculator employee);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Calculator> calculators);

    @Update
    void update(Calculator employee);

    @Delete
    void delete(Calculator employee);

    @Query("DELETE FROM calculator")
    void deleteAll();
}
