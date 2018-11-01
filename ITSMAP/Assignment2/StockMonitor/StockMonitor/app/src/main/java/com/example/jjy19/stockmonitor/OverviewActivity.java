package com.example.jjy19.stockmonitor;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.jjy19.stockmonitor.Model.StockViewModel;
import com.example.jjy19.stockmonitor.Objects.Stock;
import com.example.jjy19.stockmonitor.Service.StockService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class OverviewActivity extends AppCompatActivity {

    Button addStockButton;
    Stock newStock;
    RecyclerView stockRecyclerView;
    CustomListAdapter adapter;
    Boolean initChecker = true;

    StockViewModel stockViewModel;
    private SwipeRefreshLayout swipeContainer;

    Stock tempStock;
    JSONObject data = null;
    StockService boundService;
    List<Stock> stocks;

    int randomCounter = 0;

    SharedVariables sV;
    ServiceConnection myServiceConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        sV = (SharedVariables) getApplication();

        myServiceConnection = new ServiceConnection() {
            public void onServiceConnected(ComponentName className, IBinder service) {
                // This is called when the connection with the service has been
                // established, giving us the service object we can use to
                // interact with the service.  Because we have bound to a explicit
                // service that we know is running in our own process, we can
                // cast its IBinder to a concrete class and directly access it.
                //ref: http://developer.android.com/reference/android/app/Service.html
                boundService = ((StockService.serviceBinder) service).getService();
                sV.bound = true;
                //Log.d(LOG, "Counting service connected");

            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                boundService = null;
                sV.bound = false;
            }
        };

        // initialize ui elements
        initUIElements();

        adapter = new CustomListAdapter();
        stockRecyclerView.setAdapter(adapter);


        stockViewModel = new StockViewModel(getApplicationContext());

//        stockViewModel = ViewModelProviders.of(this).get(StockViewModel.class);
//        stockViewModel.getAllStocks().observe(this, new Observer<List<Stock>>() {
//            @Override
//            public void onChanged(@Nullable List<Stock> stocks) {
//
//                adapter.setStocks(stocks);
//            }
//        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                stockViewModel.delete(adapter.getStockAt(viewHolder.getAdapterPosition()));

                stocks = stockViewModel.getAllStocks();
                adapter.setStocks(stocks);
                adapter.notifyDataSetChanged();
                sV.Toast("Stock deleted");
            }
        }).attachToRecyclerView(stockRecyclerView);

        adapter.setOnItemClickListener(new CustomListAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Stock tempStock = stockViewModel.getAllStocks().get(position);
                goToDetailsActivity(tempStock);
            }

            @Override
            public void onItemLongClick(int position, View v) {

            }
        });

        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override

            public void onRefresh() {

                RequestQueue queue = Volley.newRequestQueue(OverviewActivity.this);
                int stockListSize = stockViewModel.getAllStocks().size();
                for (int i = 0; i < stockListSize; i++) {
                    if (stockViewModel.getAllStocks() != null) {
                        final Stock updateStock = stockViewModel.getAllStocks().get(i);
                        updateStockRequest(updateStock, queue);
                    }
                }
                swipeContainer.setRefreshing(false);
            }
        });

        // setup localBroadcastMananger
        LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(this);
        lbm.registerReceiver(receiver, new IntentFilter("filter_string"));

        addStockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToEditActivity();
            }
        });

    }


    public BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {

                String str = intent.getStringExtra("ServiceData");
                RequestQueue queue = Volley.newRequestQueue(OverviewActivity.this);
                if (initChecker)
                {
                    //stocks = stockViewModel.getAllStocks();
                    initChecker = false;
                }

                try {

                    tempStock = intent.getParcelableExtra("Stock");

                    int stockListSize = stockViewModel.getAllStocks().size();

                    if (str == "Update") {

                    } else if (str == "Add") {

                        stockViewModel.insert(tempStock);
                        updateStockRequest(tempStock, queue);

                    } else if (str == "UpdateStock") {
                        stockViewModel.update(tempStock);

                    } else if (str == "UpdateStocks") {


                        for (int i = 0; i < stockListSize; i++) {

                            if (stockViewModel.getAllStocks() != null) {

                                final Stock updateStock = stockViewModel.getAllStocks().get(i);

                                updateStockRequest(updateStock, queue);

                            }
                        }


                    } else if (str == "Delete") {
                        stockViewModel.delete(tempStock);
                    }

                    for (int i = 0; i < 10000; i++) {
                        randomCounter++;
                    }


                    stocks = stockViewModel.getAllStocks();
                    adapter.setStocks(stocks);
                    adapter.notifyDataSetChanged();

                } catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        }
    };

    public void updateStockRequest(final Stock stock, RequestQueue queue){
        String tempStockSymbol = stock.getSymbol();

        /***** Get Iextrading data for stock ******/

        String url = "https://ws-api.iextrading.com/1.0/stock/" + tempStockSymbol + "/quote";

        // Request a string response from the provided URL.
        JsonObjectRequest mRequest = new JsonObjectRequest(Request.Method.GET, url, (JSONObject) null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            double latestPrice = response.getDouble("latestPrice");

                            stock.setPrimaryExchange(latestPrice-stock.getStockPrice());
                            stock.setLatestValue(latestPrice);
                            stockViewModel.update(stock);


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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if(resultCode == RESULT_OK) {

            newStock = data.getExtras().getParcelable(sV.StockMessage);

            int tempRequestCode = data.getIntExtra("requestCode", requestCode);

            if (tempRequestCode == SharedVariables.requestCodes.Delete.getValue() )
            {
                sV.Toast("Stock has been deleted");
            }

            else if (tempRequestCode == SharedVariables.requestCodes.Add.getValue()) {
                sV.Toast("Stock has been saved!");
            }

            else if (tempRequestCode == SharedVariables.requestCodes.Update.getValue()) {
                sV.Toast("Stock has been updated!");
            }

        }

        else if (resultCode == RESULT_CANCELED){
            sV.Toast("Cancel Clicked");
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        outState.putParcelable("stockObject", newStock);
//
//        super.onSaveInstanceState(outState);
//        Log.d("Main Activity", "onSaveInstanceState called!");
//
//    }

//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//
//        Log.d("Main Activity", "onRestoreInstanceState called!");
//
//        newStock = savedInstanceState.getParcelable("stockObject");
//
//        updateUI();
//    }

    // Update is used to set text and image after a restart of the app, or a new stock is created
    private void updateUI(){

//        boundService.requestStockData();

    }

    private void goToDetailsActivity(Stock stock){
        Intent intent = new Intent(OverviewActivity.this, DetailsActivity.class);
        intent.putExtra(sV.StockMessage, stock);
        startActivityForResult(intent,sV.requestCode);
    }

    private void goToEditActivity(){
        Intent intent = new Intent(OverviewActivity.this, EditActivity.class);
        intent.putExtra(sV.StockMessage, newStock);
        intent.putExtra("DetailsData", "AddStock");
        startActivityForResult(intent,sV.requestCode);
    }

    private void initUIElements(){
        swipeContainer = findViewById(R.id.swipeContainer);
        addStockButton = findViewById(R.id.addStockBtn);
        stockRecyclerView = findViewById(R.id.stockRecyclerView);
        stockRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = new Intent(OverviewActivity.this, StockService.class);
        bindService(intent, myServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Unbind from the service
        if (sV.bound) {
            unbindService(myServiceConnection);
            sV.bound = false;
        }
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }


}
