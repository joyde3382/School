package com.example.jjy19.stockmonitor.Model;

import android.app.Application;
import android.content.Context;

import com.example.jjy19.stockmonitor.Objects.Stock;
import com.example.jjy19.stockmonitor.RoomDatabase.StockRepository;

import java.util.List;

public class StockViewModel {
    private StockRepository repository;
    private List<Stock> allStocks;

    public StockViewModel(final Context context) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    repository = new StockRepository((Application) context.getApplicationContext());
                    allStocks = repository.getAllStocks();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        Thread t = new Thread(r);
        t.start();

    }

    public void insert(Stock stock) {
        repository.insert(stock);
    }

    public void update(Stock stock) {
        repository.update(stock);
    }

    public void delete(Stock stock) {
        repository.delete(stock);
    }

    public void deleteAllStocks() {
        repository.deleteAllStocks();
    }

    public List<Stock> getAllStocks() {
        return allStocks;
    }
}