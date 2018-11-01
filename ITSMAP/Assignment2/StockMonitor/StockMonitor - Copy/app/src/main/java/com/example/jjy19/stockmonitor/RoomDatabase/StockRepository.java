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
        allStocks = stockDao.getAllStocks();
    }

    public void insert(Stock stock) {
        new InsertStockAsyncTask(stockDao).execute(stock);
    }

    public void update(Stock stock) {
        new UpdateStockAsyncTask(stockDao).execute(stock);
    }
    public void delete(Stock stock) {
        new DeleteStockAsyncTask(stockDao).execute(stock);
    }

    public void deleteAllStocks() {
        new DeleteAllStocksAsyncTask(stockDao).execute();
    }

    public LiveData<List<Stock>> getAllStocks(){
        // asynctask result list<Stock>
        // stockDao.get

        return allStocks;
    }

    private static class InsertStockAsyncTask extends AsyncTask<Stock, Void, Void> {
        private StockDAO stockDao;

        private InsertStockAsyncTask(StockDAO stockDao) {
            this.stockDao = stockDao;
        }

        @Override
        protected Void doInBackground(Stock... stocks) {
            stockDao.insert(stocks[0]);
            return null;
        }
    }

    private static class UpdateStockAsyncTask extends AsyncTask<Stock, Void, Void> {
        private StockDAO stockDao;

        private UpdateStockAsyncTask(StockDAO stockDao) {
            this.stockDao = stockDao;
        }

        @Override
        protected Void doInBackground(Stock... stocks) {
            stockDao.update(stocks[0]);
            return null;
        }
    }


    private static class DeleteStockAsyncTask extends AsyncTask<Stock, Void, Void> {
        private StockDAO stockDao;

        private DeleteStockAsyncTask(StockDAO stockDao) {
            this.stockDao = stockDao;
        }

        @Override
        protected Void doInBackground(Stock... stocks) {
            stockDao.delete(stocks[0]);
            return null;
        }
    }

    private static class DeleteAllStocksAsyncTask extends AsyncTask<Void, Void, Void> {
        private StockDAO stockDao;

        private DeleteAllStocksAsyncTask(StockDAO stockDao) {
            this.stockDao = stockDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            stockDao.deleteAllStocks();
            return null;
        }
    }

}
