package com.example.jjy19.stockmonitor.RoomDatabase;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.jjy19.stockmonitor.Objects.Stock;

@Database(entities = {Stock.class}, exportSchema = false, version = 4)
@TypeConverters({Converters.class})
public abstract class StockDatabase extends RoomDatabase {

    private static StockDatabase instance;

    public abstract StockDAO StockDao();

    public static synchronized StockDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    StockDatabase.class, "note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private StockDAO stockDao;

        private PopulateDbAsyncTask(StockDatabase db) {
            stockDao = db.StockDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            stockDao.insert(new Stock("Apple", "aapl", 100, 5, "Technology"));
            stockDao.insert(new Stock("Microsoft", "MSFT", 200, 6, "Technology"));
            stockDao.insert(new Stock("Google", "GOOGL", 300, 7, "Technology"));
            stockDao.insert(new Stock("Tesla", "TSLA", 400, 8, "Technology"));
            stockDao.insert(new Stock("Vestas", "VWS", 230, 9, "Technology"));
            stockDao.insert(new Stock("Bitcoin", "XBT", 40, 10, "Technology"));
            stockDao.insert(new Stock("Ethereum", "GDAX", 10, 11, "Technology"));
            stockDao.insert(new Stock("General Motors", "GM", 240, 12, "Technology"));
            stockDao.insert(new Stock("Sony", "SNE", 440, 13, "Technology"));
            stockDao.insert(new Stock("Amazon", "AMZN", 500, 14, "Technology"));

            return null;
        }

    }
}


//    public synchronized static StockDatabase getInstance(Context context) {
//        if (INSTANCE == null) {
//            INSTANCE = buildDatabase(context);
//        }
//        return INSTANCE;
//    }
//
//    private static StockDatabase buildDatabase(final Context context) {
//
//        return Room.databaseBuilder(context,
//                StockDatabase.class,
//                "my-database")
//                .addCallback(new Callback() {
//                    @Override
//                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
//                        super.onCreate(db);
//                        Executors.newSingleThreadScheduledExecutor().execute(
//                        new Runnable() {
//                            @Override
//                            public void run() {
//                                getInstance(context).StockDao().insertAll(Stock.populateData());
//                            }
//                        });
//                    }
//                }).build();
//    }