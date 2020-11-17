package com.example.data.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.models.Calculator;

import java.util.List;

import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Single;

@Dao
public interface CalculatorDao {
    @Query("SELECT COUNT(*) FROM calculator")
    long getCount();

    @Query("SELECT * FROM calculator")
    Single<List<Calculator>> getAll();

    @Query("SELECT * FROM calculator LIMIT :from, :size" )
    List<Calculator> getFromBySize(long from, long size);

    @Query("SELECT * FROM calculator WHERE id = :id")
    Single<Calculator> getById(String id);

    @Query("SELECT COUNT(id) FROM calculator")
    long getCalculatorsCount();

    @Insert
    void insert(Calculator calculator);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Calculator> calculators);

    @Update
    void update(Calculator calculator);

    @Delete
    void delete(Calculator calculator);

    @Query("DELETE FROM calculator")
    void deleteAll();
}
