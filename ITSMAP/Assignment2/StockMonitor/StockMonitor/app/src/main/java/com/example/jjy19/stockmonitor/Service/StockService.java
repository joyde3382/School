package com.example.jjy19.stockmonitor.Service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.v4.content.LocalBroadcastManager;

import com.example.jjy19.stockmonitor.Model.StockViewModel;
import com.example.jjy19.stockmonitor.Objects.Stock;

import java.util.ArrayList;
import java.util.List;

public class StockService extends Service {

    private List<Stock> stocks;
    StockViewModel stockViewModel;
    Stock newStock;
    Context context = this;
    private String TAG = StockService.class.getSimpleName();

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

                        while (true) {

                            Intent intent = new Intent();
                            intent.setAction("filter_string");
                            intent.putExtra("ServiceData", "UpdateStocks");

                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

                            Thread.sleep(20000);
                        }
                    } catch(Exception e){
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
            }
        };
        Thread t = new Thread(r);
        t.start();
    }

    @Override
    public IBinder onBind(Intent intent) {

        newStock = intent.getParcelableExtra("stock");
        return binder;
    }

    public void deleteStock(final Stock stock){

        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {

                    Intent intent = new Intent();
                    intent.setAction("filter_string");
                    intent.putExtra("Stock", stock);
                    intent.putExtra("ServiceData", "Delete");

                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        Thread t = new Thread(r);
        t.start();
    }

    public void addStock(final Stock stock){

        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {

                    Intent intent = new Intent();
                    intent.setAction("filter_string");
                    intent.putExtra("Stock", stock);
                    intent.putExtra("ServiceData", "Add");

                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        Thread t = new Thread(r);
        t.start();
    }

    public void getStocks(){

        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {

                    stocks = stockViewModel.getAllStocks().getValue();

                    Intent intent = new Intent();
                    intent.setAction("filter_string");
                    intent.putParcelableArrayListExtra("ServiceStockList", (ArrayList<? extends Parcelable>) stocks);
                    intent.putExtra("ServiceData", "Update");

                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        Thread t = new Thread(r);
        t.start();
    }

    public void updateStock(final Stock stock){

        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {

                    Intent intent = new Intent();
                    intent.setAction("filter_string");
                    intent.putExtra("Stock", stock);
                    intent.putExtra("ServiceData", "UpdateStock");

                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        Thread t = new Thread(r);
        t.start();
    }
}
