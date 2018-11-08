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

import com.example.jjy19.stockmonitor.Objects.Stock;
import com.example.jjy19.stockmonitor.Service.StockService;

import org.json.JSONObject;

import java.util.List;

public class OverviewActivity extends AppCompatActivity {

    Button addStockButton;
    Stock newStock;
    RecyclerView stockRecyclerView;
    CustomListAdapter adapter;

    private SwipeRefreshLayout swipeContainer;

    JSONObject data = null;
    StockService boundService;
    List<Stock> stocks;

    SharedVariables sV;
    ServiceConnection myServiceConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        // initialize ui elements
        initElements();

        // initialize StockService
        initServiceConnection();


        // setup onSwipe feature for delete stocks by swipping them to the left or right
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                boundService.deleteStock(adapter.getStockAt(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(stockRecyclerView);

        // onClickListener for editting a specific stock
        adapter.setOnItemClickListener(new CustomListAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Stock tempStock = stocks.get(position);
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
                boundService.updateStocks();
                swipeContainer.setRefreshing(false);
            }
        });


        // add new stock
        addStockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToEditActivity();
            }
        });

    }

    // broadcastReciver, handling broadcasts from StockService
    public BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {

                // not currently using this, however keeping it for good practice
                String str = intent.getStringExtra("ServiceData");


                if (str == "404")
                {
                    sV.Toast("Invalid Symbol, please try again");
                }

                else{

                    try {

                        // since we're not allowed to use liveData to update our list, this is the way to go
                        // reload the entire thing..
                        stocks = intent.getParcelableArrayListExtra("ServiceStockList");
                        adapter.setStocks(stocks);

                        adapter.notifyDataSetChanged();


                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        }
    };

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

    private void initElements(){
        swipeContainer = findViewById(R.id.swipeContainer);
        addStockButton = findViewById(R.id.addStockBtn);
        stockRecyclerView = findViewById(R.id.stockRecyclerView);
        stockRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // setup adapter and recyclerView
        adapter = new CustomListAdapter();
        stockRecyclerView.setAdapter(adapter);

        // setup localBroadcastMananger
        LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(this);
        lbm.registerReceiver(receiver, new IntentFilter("filter_string"));

        // shared variables object
        sV = (SharedVariables)getApplication();
    }

    private void initServiceConnection(){
        myServiceConnection = new ServiceConnection() {
            public void onServiceConnected(ComponentName className, IBinder service) {
                // This is called when the connection with the service has been established

                // mapping the service to my serviceObject
                boundService = ((StockService.serviceBinder) service).getService();
                boundService.getStocks();
                sV.bound = true;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                boundService = null;
                sV.bound = false;
            }
        };

        Intent intent = new Intent(this, StockService.class);
        startService(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // bind the activity to the service
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
