package com.example.jjy19.stockmonitor.Model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.jjy19.stockmonitor.Objects.Stock;
import com.example.jjy19.stockmonitor.RoomDatabase.StockRepository;

import java.util.List;

public class StockViewModel extends AndroidViewModel {
    private StockRepository repository;
    private LiveData<List<Stock>> allStocks;

    public StockViewModel(@NonNull Application application) {
        super(application);
        repository = new StockRepository(application);
        allStocks = repository.getAllStocks();
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

    public LiveData<List<Stock>> getAllStocks() {
        return allStocks;
    }
}