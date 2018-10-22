package com.example.jjy19.stockmonitor.Service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.example.jjy19.stockmonitor.Objects.Stock;
import com.example.jjy19.stockmonitor.R;
import com.example.jjy19.stockmonitor.RoomDatabase.StockDatabase;
import com.example.jjy19.stockmonitor.OverviewActivity;

import java.util.List;

public class StockService extends Service {

    private List<Stock> stocks;


    StockDatabase db;
    Stock newStock;
    Context context = this;

    public class serviceBinder extends Binder {
        public StockService getService() {
            return  StockService.this;
        }
    }

    private  final IBinder binder = new serviceBinder();

    public StockService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //note that the onCreate() is only called when service is first bound to (=started)
        //this will only run once in the services life time

        //create a good ol' java Thread and let it sleep for a second and count up in a loop
        //first we create the runnable, then we start the thread
        Runnable r = new Runnable() {
            @Override
            public void run() {
                    try {

                        db = Room.databaseBuilder(getApplicationContext(),
                                StockDatabase.class, "Task-database").build();
                        stocks = db.StockDao().getAll();
                        stocks.add(newStock);
                        db.StockDao().insertAll(newStock);

                        Intent intent = new Intent();
                        intent.setAction("filter_string");
                        intent.putExtra("ServiceStockList", (Parcelable) stocks);
                        intent.putExtra("ServiceData", "Update ListView");

                        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

                        // String currentDBPath = getDatabasePath("StockDatabase").getAbsolutePath();
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
            }
        };
        Thread t = new Thread(r);
        t.start();
    }

    @Override
    //very important! return your IBinder (your custom Binder)
    public IBinder onBind(Intent intent) {

        newStock = intent.getParcelableExtra("stock");
        return binder;
    }

}
