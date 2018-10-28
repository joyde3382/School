package com.example.jjy19.stockmonitor.Service;

import android.app.Service;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.jjy19.stockmonitor.Objects.Stock;
import com.example.jjy19.stockmonitor.RoomDatabase.StockDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StockService extends Service {

    private List<Stock> stocks;



    Stock newStock;
    Context context = this;
    JSONObject data = null;
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

                        StockDatabase db = null;
//                        db = Room.




                        while (true) {

                            stocks = db.StockDao().getAll();

                            RequestQueue queue = Volley.newRequestQueue(StockService.this);

                            for (int i = 0; i < stocks.size(); i++) {

                                final Stock tempStock = stocks.get(i);

                                String tempStockSymbol = tempStock.getSymbol();

                                /***** Get Iextrading data for stock ******/

                                String url = "https://ws-api.iextrading.com/1.0/stock/" + tempStockSymbol + "/delayed-quote";

                                // Request a string response from the provided URL.
                                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {

//                                                Toast toast = Toast.makeText(getApplicationContext(), "Recieved response string", Toast.LENGTH_SHORT);
//                                                toast.show();

                                                try {
                                                    data = new JSONObject(response);
                                                } catch (Exception e) {
                                                    System.out.println("Exception " + e.getMessage());
                                                }

                                                try {

                                                    double delayedPrice = data.getDouble("delayedPrice");

                                                    if (delayedPrice != tempStock.getStockPrice()) {

                                                        tempStock.setStockPrice(delayedPrice);

                                                        updateStock(tempStock);

                                                        Intent intent = new Intent();
                                                        intent.setAction("filter_string");
//                                                        intent.putExtra("Stock", tempStock);
                                                        intent.putParcelableArrayListExtra("ServiceStockList", (ArrayList<? extends Parcelable>) stocks);
                                                        intent.putExtra("ServiceData", "UpdateStock");

                                                        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                                                    }

                                                } catch (final JSONException e) {
                                                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                                                }
                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                        Toast toast = Toast.makeText(getApplicationContext(), "That didn't work!", Toast.LENGTH_SHORT);
                                        toast.show();

                                    }
                                });


                                /***** Get price data ******/



                                // Add the request to the RequestQueue.
                                queue.add(stringRequest);

                            }
                            Thread.sleep(5000);
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
    //very important! return your IBinder (your custom Binder)
    public IBinder onBind(Intent intent) {

        newStock = intent.getParcelableExtra("stock");
        return binder;
    }


    public void deleteStock(final Stock stock){

        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {

                    db.StockDao().delete(stock);
                    stocks = db.StockDao().getAll();

                    Intent intent = new Intent();
                    intent.setAction("filter_string");
//                    intent.putExtra("Stock", stock);
                    intent.putParcelableArrayListExtra("ServiceStockList", (ArrayList<? extends Parcelable>) stocks);
                    intent.putExtra("ServiceData", "Delete");

                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

                } catch (Exception e) {
                    // TODO Auto-generated catch block
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
                    stocks = db.StockDao().getAll();

                    Intent intent = new Intent();
                    intent.setAction("filter_string");
                    intent.putExtra("Stock", stock);
                    intent.putParcelableArrayListExtra("ServiceStockList", (ArrayList<? extends Parcelable>) stocks);
                    intent.putExtra("ServiceData", "Add");

                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        };
        Thread t = new Thread(r);
        t.start();
    }

//    public void addStocks(final List<Stock> stockList){
//
//        Runnable r = new Runnable() {
//            @Override
//            public void run() {
//                try {
//
//                    db.StockDao().insertAll(stockList);
//                    stocks = db.StockDao().getAll();
//
//                    Intent intent = new Intent();
//                    intent.setAction("filter_string");
////                    intent.putExtra("Stock", stock);
//                    intent.putParcelableArrayListExtra("ServiceStockList", (ArrayList<? extends Parcelable>) stocks);
//                    intent.putExtra("ServiceData", "AddList");
//
//                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
//
//                } catch (Exception e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//            }
//        };
//        Thread t = new Thread(r);
//        t.start();
//    }

    public void getStocks(){

        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {

                    stocks = db.StockDao().getAll();

                    Intent intent = new Intent();
                    intent.setAction("filter_string");
                    intent.putParcelableArrayListExtra("ServiceStockList", (ArrayList<? extends Parcelable>) stocks);
                    intent.putExtra("ServiceData", "Update");

                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

                } catch (Exception e) {
                    // TODO Auto-generated catch block
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

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        };
        Thread t = new Thread(r);
        t.start();
    }

}
