package com.example.jjy19.stockmonitor.RoomDatabase;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.jjy19.stockmonitor.Objects.Stock;

import java.util.List;

public class StockRepository {
    private StockDAO stockDao;
    private LiveData<List<Stock>> allStocks;

    public StockRepository(Application application) {
        StockDatabase db = StockDatabase.getInstance(application);
        stockDao = db.StockDao();
        allStocks = stockDao.getAll();
    }

    public void insert(Stock stock) {
        new InsertNoteAsyncTask(stockDao).execute(stock);
    }

    public void update(Stock stock) {
        new UpdateNoteAsyncTask(stockDao).execute(stock);
    }
    public void delete(Stock stock) {
        new DeleteNoteAsyncTask(stockDao).execute(stock);
    }
    public void deleteAllNotes(Stock stock) {
        new DeleteAllNotesAsyncTask(stockDao).execute();
    }

    public LiveData<List<Stock>> getAllStocks(){
        return allStocks;
    }

    private static class InsertNoteAsyncTask extends AsyncTask<Stock, Void, Void> {
        private StockDAO stockDao;

        private InsertNoteAsyncTask(StockDAO stockDao) {
            this.stockDao = stockDao;
        }

        @Override
        protected Void doInBackground(Stock... stocks) {
            stockDao.insert(stocks[0]);
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<Stock, Void, Void> {
        private StockDAO stockDao;

        private UpdateNoteAsyncTask(StockDAO stockDao) {
            this.stockDao = stockDao;
        }

        @Override
        protected Void doInBackground(Stock... stocks) {
            stockDao.update(stocks[0]);
            return null;
        }
    }


    private static class DeleteNoteAsyncTask extends AsyncTask<Stock, Void, Void> {
        private StockDAO stockDao;

        private DeleteNoteAsyncTask(StockDAO stockDao) {
            this.stockDao = stockDao;
        }

        @Override
        protected Void doInBackground(Stock... stocks) {
            stockDao.update(stocks[0]);
            return null;
        }
    }

    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void, Void, Void> {
        private StockDAO stockDao;

        private DeleteAllNotesAsyncTask(StockDAO stockDao) {
            this.stockDao = stockDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            stockDao.update();
            return null;
        }
    }

}
