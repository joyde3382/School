package com.example.jjy19.stockmonitor.RoomDatabase;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.jjy19.stockmonitor.Objects.Stock;

import java.util.List;

// DAO interface with standard CRUDs
@Dao
public interface StockDAO {

    @Query("SELECT * FROM stock_table")
    List<Stock> getAllStocks();

    @Query("select * from stock_table where sid in (:StockIDs)")
    List<Stock> loadAllByIds(int[] StockIDs);

    @Insert
    void insertAll(Stock... stocks);

    @Insert
    void insert(Stock... stocks);

    @Update
    void update(Stock... stocks);

    @Delete
    void delete(Stock... stocks);

    @Query("DELETE FROM stock_table")
    void deleteAllStocks();




}


