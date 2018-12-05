package com.example.jjy19.stockmonitor.Service;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.jjy19.stockmonitor.Objects.Stock;
import com.example.jjy19.stockmonitor.R;
import com.example.jjy19.stockmonitor.RoomDatabase.StockDatabase;
import com.example.jjy19.stockmonitor.RoomDatabase.StockRepository;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class StockService extends Service {

    private List<Stock> stocks;
    Stock newStock;
    Context context = this;
    StockDatabase db;
    RequestQueue queue;
    private StockRepository repository;
    NotificationCompat.Builder notificationBuilder;

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

        // onCreate will be call once, when an activity first binds to the service
        // from now on the service will run in a never ending thread
        Runnable r = new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void run() {
                    try {

                        db = StockDatabase.getInstance(getApplication());
                        queue = Volley.newRequestQueue(StockService.this);
                        repository = new StockRepository((Application) context.getApplicationContext());

                        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy: " + "HH:mm:ss");

                        while (true) {

                            // check stocks in db for new data
                            updateStocks();

                            Date c = Calendar.getInstance().getTime();
                            String formattedDate = df.format(c);

                            // write notification to used with latests stock update time stamp
                            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                NotificationChannel channel = new NotificationChannel("updateChannel", "updateChannel", NotificationManager.IMPORTANCE_LOW );
                                notificationManager.createNotificationChannel(channel);
                                notificationBuilder = new NotificationCompat.Builder(StockService.this, "updateChannel").setChannelId("updateChannel");
                            }
                            else {
                                notificationBuilder = new NotificationCompat.Builder(StockService.this);
                            }

                            notificationBuilder.setOnlyAlertOnce(true)
                                    .setContentTitle("Last stock update: ")
                                    .setContentText(formattedDate)
                                    .setSmallIcon(R.drawable.materials)
                                    .setAutoCancel(true);


                            notificationManager.notify(0, notificationBuilder.build());

                            // make thread sleep for 2 minutes
                            Thread.sleep(120000);
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

    //gets data from Iextrading in batches
    public void updateStocks() {
        try {

            stocks = repository.getAllStocks();

            final int stockListSize = stocks.size();

            if (stocks.size() != 0) {

                String BatchOfSymbols = "";
                Stock tempStock;

                for (int i = 0; i < stockListSize; i++) {
                    tempStock = stocks.get(i);
                    BatchOfSymbols = BatchOfSymbols + tempStock.getSymbol() + ",";
                }

                BatchOfSymbols = BatchOfSymbols.substring(0, BatchOfSymbols.length()-1);

                /***** Get Iextrading data for stock ******/

                String url = "https://ws-api.iextrading.com/1.0//stock/market/batch?symbols=" + BatchOfSymbols + "&types=quote";

                // Request a string response from the provided URL.
                JsonObjectRequest mRequest = new JsonObjectRequest(Request.Method.GET, url, (JSONObject) null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                    Stock updateStock;
                                    double latestPrice;
                                    JSONObject tempJSONObject;

                                    for (int i = 0; i < stockListSize; i++) {
                                        try {
                                            updateStock = stocks.get(i);

                                            tempJSONObject = response.getJSONObject(updateStock.getSymbol().toUpperCase());

                                            tempJSONObject = tempJSONObject.getJSONObject("quote");
                                            latestPrice = tempJSONObject.getDouble("latestPrice");

                                            updateStock.setSellValue(latestPrice - updateStock.getStockPrice());
                                            updateStock.setLatestValue(tempJSONObject.getDouble("latestPrice"));
                                            updateStock.setStockSector(tempJSONObject.getString("sector"));
                                            updateStock.setPrimaryExchange(tempJSONObject.getString("primaryExchange"));

                                            repository.update(updateStock);

                                        } catch (final JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    Intent intent = new Intent();
                                    intent.setAction("filter_string");
                                    intent.putExtra("ServiceData", "UpdateStocks");
                                    intent.putParcelableArrayListExtra("ServiceStockList", (ArrayList<? extends Parcelable>) stocks);

                                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                            }

                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

                // Add the request to the RequestQueue.
                queue.add(mRequest);

            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void deleteStock(final Stock stock){

        try {

            repository.delete(stock);
            stocks = repository.getAllStocks();

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

    public void addStock(final Stock stock){

        String symbol = stock.getSymbol();

        /***** Get Iextrading data for stock ******/

        String url = "https://ws-api.iextrading.com/1.0/stock/" + symbol + "/quote";

        JsonObjectRequest mRequest = new JsonObjectRequest(Request.Method.GET, url, (JSONObject) null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            // Update the stock with data from Iextrading, then insert to DB

                            double latestPrice = response.getDouble("latestPrice");
                            String sector = response.getString("sector");

                            stock.setSellValue(latestPrice - stock.getStockPrice());
                            stock.setLatestValue(latestPrice);
                            stock.setStockSector(sector);
                            stock.setPrimaryExchange(response.getString("primaryExchange"));

                            repository.insert(stock);

                            stocks = repository.getAllStocks();

                            Intent intent = new Intent();
                            intent.setAction("filter_string");
                            intent.putExtra("ServiceData", "Add");
                            intent.putParcelableArrayListExtra("ServiceStockList", (ArrayList<? extends Parcelable>) stocks);

                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

                        } catch (final JSONException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                Intent intent = new Intent();
                intent.setAction("filter_string");
                intent.putExtra("ServiceData", "404");

                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            }
        });

        // Add the request to the RequestQueue.
        queue.add(mRequest);

    }

    public void getStocks(){
        try {

            stocks = repository.getAllStocks();

            Intent intent = new Intent();
            intent.setAction("filter_string");
            intent.putParcelableArrayListExtra("ServiceStockList", (ArrayList<? extends Parcelable>) stocks);
            intent.putExtra("ServiceData", "Update");

            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateStock(final Stock stock){
        try {

            String symbol = stock.getSymbol();

            /***** Get Iextrading data for stock ******/

            String url = "https://ws-api.iextrading.com/1.0/stock/" + symbol + "/quote";

            JsonObjectRequest mRequest = new JsonObjectRequest(Request.Method.GET, url, (JSONObject) null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {

                                // Update the stock with data from Iextrading

                                double latestPrice = response.getDouble("latestPrice");

                                stock.setSellValue(latestPrice-stock.getStockPrice());
                                stock.setLatestValue(latestPrice);

                                repository.update(stock);

                                stocks = repository.getAllStocks();

                                Intent intent = new Intent();
                                intent.setAction("filter_string");
                                intent.putExtra("ServiceData", "UpdateStock");
                                intent.putParcelableArrayListExtra("ServiceStockList", (ArrayList<? extends Parcelable>) stocks);

                                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

                            } catch (final JSONException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (ExecutionException e) {
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
