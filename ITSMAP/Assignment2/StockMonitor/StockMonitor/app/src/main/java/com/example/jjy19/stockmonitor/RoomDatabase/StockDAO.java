package com.example.jjy19.stockmonitor.RoomDatabase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.jjy19.stockmonitor.Objects.Stock;

import java.util.List;

@Dao
public interface StockDAO {

    @Query("SELECT * FROM stock")
    List<Stock> getAll();

    @Query("select * from stock where sid in (:StockIDs)")
    List<Stock> loadAllByIds(int[] StockIDs);

    @Insert
    void insertAll(Stock... stocks);

    @Update
    void update(Stock... stock);

    @Delete
    void delete(Stock... stock);
}
