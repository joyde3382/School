package com.example.jjy19.stockmonitor.RoomDatabase;

import android.app.Application;
import android.os.AsyncTask;

import com.example.jjy19.stockmonitor.Objects.Stock;

import java.util.List;
import java.util.concurrent.ExecutionException;

// this repository class is used to interface between the service and the database (using the DAO)
// it makes sure the database interactions is always run in a thread (asyncTask)
public class StockRepository {
    private StockDAO stockDao;
    StockDatabase db;

    public StockRepository(final Application application) {
        db = StockDatabase.getInstance(application);
        stockDao = db.StockDao();
        List<Stock> allStocks = stockDao.getAllStocks();
    }

    public void insert(Stock stock) {
        // calls an asyncTask function which inserts the stock into the db
        new InsertStockAsyncTask(stockDao).execute(stock);
    }

    public void update(Stock stock) {
        // calls an asyncTask function which updates the stock in the db
        new UpdateStockAsyncTask(stockDao).execute(stock);
    }

    public void delete(Stock stock) {
        // calls an asyncTask function which deletes the stock in the db
        new DeleteStockAsyncTask(stockDao).execute(stock);
    }

    public void deleteAllStocks() {
        new DeleteAllStocksAsyncTask(stockDao).execute();
    }

    public List<Stock> getAllStocks() throws ExecutionException, InterruptedException {
        // calls an asyncTask function which returns all stocks from the db
        return new getAllStocksAsyncTask(stockDao).execute().get();
    }

    private static class getAllStocksAsyncTask extends AsyncTask<Void, Void, List<Stock>> {
        private StockDAO stockDao;

        private getAllStocksAsyncTask(StockDAO stockDao) {
            this.stockDao = stockDao;
        }

        @Override
        protected List<Stock> doInBackground(Void... voids) {

            return stockDao.getAllStocks();
        }
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
