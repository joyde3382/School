package com.example.jjy19.stockmonitor;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jjy19.stockmonitor.Objects.Stock;
import com.example.jjy19.stockmonitor.RoomDatabase.StockDatabase;
import com.example.jjy19.stockmonitor.Service.StockService;

import java.util.ArrayList;
import java.util.List;

public class OverviewActivity extends SharedVariables {

    Button addStockButton;
    TextView nameText, priceText;
    Stock newStock;
    ImageView sectorImage;
    ListView stockListView;


    private StockService boundService;
    //private ServiceConnection myServiceConnection;
    private AsyncTask mMyTask;
    // private List<Stock> stocks;
    List<Stock> stocks;
    StockDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        myServiceConnection = new ServiceConnection() {
            public void onServiceConnected(ComponentName className, IBinder service) {
                // This is called when the connection with the service has been
                // established, giving us the service object we can use to
                // interact with the service.  Because we have bound to a explicit
                // service that we know is running in our own process, we can
                // cast its IBinder to a concrete class and directly access it.
                //ref: http://developer.android.com/reference/android/app/Service.html
                boundService = ((StockService.serviceBinder) service).getService();
                //Log.d(LOG, "Counting service connected");

            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                boundService = null;
            }
        };

        // initialize ui elements
        initUIElements();

        LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(this);
        lbm.registerReceiver(receiver, new IntentFilter("filter_string"));

        newStock = getIntent().getParcelableExtra(StockMessage); // TODO Get from db instead



        Intent intent = new Intent(OverviewActivity.this, StockService.class);
        bindService(intent, myServiceConnection, Context.BIND_AUTO_CREATE);
        bound = true;


        // update the stock object (if phone is flipped/app is restarted)


        addStockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToEditActivity();
            }
        });

        // TODO put in update()

        stockListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Stock tempStock = stocks.get(position);

                goToDetailsActivity(tempStock);
            }
        });

        new CountDownTimer(400, 400) {

            @Override
            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                updateUI();
            }
        }.start();


    }


    public BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {

                String str = intent.getStringExtra("ServiceData");

                newStock = intent.getParcelableExtra("Stock");



                CustomListAdapter adapter = new CustomListAdapter(OverviewActivity.this, R.layout.adapter_layout, (ArrayList<Stock>) stocks);
                try {

                    if (str == "Update") {
//                        stockListView.setAdapter(adapter);
                        stocks = intent.getParcelableArrayListExtra("ServiceStockList"); // TODO Fix placement (is sometimes null)
                    } else if (str == "UpdateStock") {
                        adapter.insert(newStock, newStock.getSid());
                        stockListView.setAdapter(adapter);
                    } else if (str == "Add") {
                        adapter.add(newStock);
                    } else if (str == "Delete") {
                        adapter.remove(newStock);
                    }

                    stockListView.setAdapter(adapter);

                } catch (Exception e)
                {
                    e.printStackTrace();
                }

                Toast(str);

            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if(resultCode == RESULT_OK) {

            newStock = data.getExtras().getParcelable(StockMessage);

            int tempRequestCode = data.getIntExtra("requestCode", requestCode);


            if (tempRequestCode == requestCodes.Delete.getValue() )
            {
                Toast("Stock has been deleted");
                boundService.deleteStock(newStock);

            }

            else if (tempRequestCode == requestCodes.Add.getValue()) {
                Toast("Stock has been saved!");

                boundService.addStock(newStock);

            }

            updateUI();
        }

        else if (resultCode == RESULT_CANCELED){
            Toast("Cancel Clicked");
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("stockObject", newStock);

        super.onSaveInstanceState(outState);
        Log.d("Main Activity", "onSaveInstanceState called!");

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        Log.d("Main Activity", "onRestoreInstanceState called!");

        newStock = savedInstanceState.getParcelable("stockObject");

        updateUI();
    }

    // Update is used to set text and image after a restart of the app, or a new stock is created
    private void updateUI(){

        boundService.getStocks();

    }

    private void goToDetailsActivity(Stock stock){
        Intent intent = new Intent(OverviewActivity.this, DetailsActivity.class);
        intent.putExtra(StockMessage, stock);
        startActivityForResult(intent,requestCode);
    }

    private void goToEditActivity(){
        Intent intent = new Intent(OverviewActivity.this, EditActivity.class);
        intent.putExtra(StockMessage, newStock);
        startActivityForResult(intent,requestCode);
    }

    private void initUIElements(){
        nameText = findViewById(R.id.overView_stockName);
        priceText = findViewById(R.id.overView_stockPrice);
        sectorImage = findViewById(R.id.overView_sectorPic);
        addStockButton = findViewById(R.id.addStockBtn);
        stockListView = findViewById(R.id.stockListView);
    }

    @Override
    protected void onDestroy() {

        unbindService(myServiceConnection);
        bound = false;

        super.onDestroy();
    }
}