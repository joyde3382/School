package com.example.jjy19.stockmonitor.Service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.v4.content.LocalBroadcastManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.jjy19.stockmonitor.Model.StockViewModel;
import com.example.jjy19.stockmonitor.Objects.Stock;
import com.example.jjy19.stockmonitor.OverviewActivity;
import com.example.jjy19.stockmonitor.RoomDatabase.StockDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StockService extends Service {

    private List<Stock> stocks;
    StockViewModel stockViewModel;
    Stock newStock;
    Context context = this;
    StockDatabase db;

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

                        db = StockDatabase.getInstance(getApplication());
                        RequestQueue queue = Volley.newRequestQueue(StockService.this);

                        while (true) {

                            updateStocks(queue);

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

    public void updateStocks(final RequestQueue queue){

        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    stocks = db.StockDao().getAllStocks();

                    int stockListSize = stocks.size();

                    for (int i = 0; i < stockListSize; i++) {

                        if (stocks.size() != 0) {

                            final Stock updateStock = stocks.get(i);

                            String tempStockSymbol = updateStock.getSymbol();

                            /***** Get Iextrading data for stock ******/

                            String url = "https://ws-api.iextrading.com/1.0/stock/" + tempStockSymbol + "/quote";

                            // Request a string response from the provided URL.
                            JsonObjectRequest mRequest = new JsonObjectRequest(Request.Method.GET, url, (JSONObject) null,
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            try {

                                                double latestPrice = response.getDouble("latestPrice");

                                                updateStock.setPrimaryExchange(latestPrice-updateStock.getStockPrice());
                                                updateStock.setLatestValue(latestPrice);
                                                updateStock(updateStock);


                                            } catch (final JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }

                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            });

                            // Add the request to the RequestQueue.
                            queue.add(mRequest);

                            Intent intent = new Intent();
                            intent.setAction("filter_string");
                            intent.putExtra("ServiceData", "UpdateStocks");
                            intent.putParcelableArrayListExtra("ServiceStockList", (ArrayList<? extends Parcelable>) stocks);

                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

                        }
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

    public void deleteStock(final Stock stock){

        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {

                    db.StockDao().delete(stock);
                    stocks = db.StockDao().getAllStocks();

                    Intent intent = new Intent();
                    intent.setAction("filter_string");
//                    intent.putExtra("Stock", stock);
                    intent.putExtra("ServiceData", "Delete");
                    intent.putParcelableArrayListExtra("ServiceStockList", (ArrayList<? extends Parcelable>) stocks);

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

                    db.StockDao().insert(stock);
                    stocks = db.StockDao().getAllStocks();

                    Intent intent = new Intent();
                    intent.setAction("filter_string");
//                    intent.putExtra("Stock", stock);
                    intent.putExtra("ServiceData", "Add");
                    intent.putParcelableArrayListExtra("ServiceStockList", (ArrayList<? extends Parcelable>) stocks);

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

                    stocks = stockViewModel.getAllStocks();

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

                    db.StockDao().update(stock);
                    stocks = db.StockDao().getAllStocks();

                    Intent intent = new Intent();
                    intent.setAction("filter_string");
//                    intent.putExtra("Stock", stock);
                    intent.putExtra("ServiceData", "UpdateStock");
                    intent.putParcelableArrayListExtra("ServiceStockList", (ArrayList<? extends Parcelable>) stocks);

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
