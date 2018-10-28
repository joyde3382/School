package com.example.jjy19.stockmonitor.RoomDatabase;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.support.annotation.NonNull;

import com.example.jjy19.stockmonitor.Objects.Stock;

@Database(entities = {Stock.class}, exportSchema = false, version = 4)
@TypeConverters({Converters.class})
public abstract class StockDatabase extends RoomDatabase {

    private static StockDatabase INSTANCE;

    public abstract StockDAO StockDao();

    public synchronized static StockDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = buildDatabase(context);
        }
        return INSTANCE;
    }

    private static StockDatabase buildDatabase(final Context context) {

        return Room.databaseBuilder(context,
                StockDatabase.class,
                "my-database")
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
//                        Executors.newSingleThreadScheduledExecutor().execute(
                        new Runnable() {
                            @Override
                            public void run() {
                                getInstance(context).StockDao().insertAll(Stock.populateData());
                            }
                        };
                    }
                }).build();
    }
}
