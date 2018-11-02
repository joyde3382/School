package com.example.jjy19.stockmonitor.RoomDatabase;

import android.app.Application;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v4.content.LocalBroadcastManager;

import com.example.jjy19.stockmonitor.Objects.Stock;
import com.example.jjy19.stockmonitor.SharedVariables;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class StockRepository {
    private StockDAO stockDao;
    private List<Stock> allStocks;
    StockDatabase db;

    public StockRepository(final Application application) {
        // run in asynctask
        db = StockDatabase.getInstance(application);
        stockDao = db.StockDao();
        allStocks = stockDao.getAllStocks();
    }

  //  public StockDatabase initDb(Application application){
    //    new initDbAsyncTask(application).execute();
    //}

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

    public List<Stock> getAllStocks() throws ExecutionException, InterruptedException {

        return new getAllStocksAsyncTask(stockDao).execute().get();
    }

    private static class getAllStocksAsyncTask extends AsyncTask<Void, Void, List<Stock>> {
        private StockDAO stockDao;
        SharedVariables sv = SharedVariables.getInstance();

        private getAllStocksAsyncTask(StockDAO stockDao) {
            this.stockDao = stockDao;
        }

        @Override
        protected void onPostExecute(List<Stock> stocks) {
            sv.setDataReady(true);
            super.onPostExecute(stocks);
        }


        @Override
        protected List<Stock> doInBackground(Void... voids) {

            return stockDao.getAllStocks();
        }
    }

    private static class InsertStockAsyncTask extends AsyncTask<Stock, Void, Void> {
        private StockDAO stockDao;
        SharedVariables sv = SharedVariables.getInstance();

        private InsertStockAsyncTask(StockDAO stockDao) {
            this.stockDao = stockDao;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
             sv.setDataReady(true);

            super.onPostExecute(aVoid);

        }

        @Override
        protected Void doInBackground(Stock... stocks) {
            stockDao.insert(stocks[0]);
            return null;
        }
    }

    private static class UpdateStockAsyncTask extends AsyncTask<Stock, Void, Void> {
        private StockDAO stockDao;
        SharedVariables sv = SharedVariables.getInstance();

        private UpdateStockAsyncTask(StockDAO stockDao) {
            this.stockDao = stockDao;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            sv.setDataReady(true);

            super.onPostExecute(aVoid);

        }

        @Override
        protected Void doInBackground(Stock... stocks) {
            stockDao.update(stocks[0]);
            return null;
        }
    }


    private static class DeleteStockAsyncTask extends AsyncTask<Stock, Void, Void> {
        private StockDAO stockDao;
        SharedVariables sv = SharedVariables.getInstance();

        private DeleteStockAsyncTask(StockDAO stockDao) {
            this.stockDao = stockDao;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            sv.setDataReady(true);

            super.onPostExecute(aVoid);

        }

        @Override
        protected Void doInBackground(Stock... stocks) {
            stockDao.delete(stocks[0]);
            return null;
        }
    }

    private static class DeleteAllStocksAsyncTask extends AsyncTask<Void, Void, Void> {
        private StockDAO stockDao;
        SharedVariables sv = SharedVariables.getInstance();

        private DeleteAllStocksAsyncTask(StockDAO stockDao) {
            this.stockDao = stockDao;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            sv.setDataReady(true);

            super.onPostExecute(aVoid);

        }

        @Override
        protected Void doInBackground(Void... voids) {
            stockDao.deleteAllStocks();
            return null;
        }
    }

}
