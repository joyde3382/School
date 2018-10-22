package com.example.jjy19.stockmonitor.RoomDatabase;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.example.jjy19.stockmonitor.Objects.Stock;

@Database(entities = {Stock.class}, exportSchema = false, version = 4)
@TypeConverters({Converters.class})
public abstract class StockDatabase extends RoomDatabase {
    public abstract StockDAO StockDao();
}
