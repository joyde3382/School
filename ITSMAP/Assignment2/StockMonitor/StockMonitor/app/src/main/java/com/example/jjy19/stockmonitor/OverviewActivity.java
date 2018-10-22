package com.example.jjy19.stockmonitor;

import android.annotation.SuppressLint;
import android.arch.persistence.room.Room;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

    boolean bound = false;
    private StockService boundService;
    private ServiceConnection myServiceConnection;
    private AsyncTask mMyTask;
    private List<Stock> stocks;
    StockDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        // initialize ui elements
        initUIElements();


        LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(this);
        lbm.registerReceiver(receiver, new IntentFilter("filter_string"));

        //newStock = getIntent().getParcelableExtra(StockMessage); // TODO Get from db instead

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

        Intent intent = new Intent(OverviewActivity.this, StockService.class);
        intent.putExtra(StockMessage, newStock);

        bindService(intent, myServiceConnection, Context.BIND_AUTO_CREATE);
        bound = true;

        // update the stock object (if phone is flipped/app is restarted)
        update(newStock);

        addStockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToEditActivity();
            }
        });

        ArrayList<Stock> stockList = new ArrayList<>();


        stockList.add(newStock); // TODO load list from db

        CustomListAdapter adapter = new CustomListAdapter(this, R.layout.adapter_layout,stockList);
        // TODO put in update()
        stockListView.setAdapter(adapter);
        stockListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        goToDetailsActivity();
                        break;

                    case 1:
                        goToDetailsActivity();
                        break;

                    case 2:
                        goToDetailsActivity();
                        break;

                }
            }
        });



    }


    public BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                String str = intent.getStringExtra("ServiceData");

                List<Stock> stocks = intent.getParcelableExtra("ServiceStockList");
                if (str == "Update ListView")
                {
                    // stockListView.
                }
                Toast(str);
                // get all your data from intent and do what you want
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode == RESULT_OK) {

            Toast("Stock has been saved!");
            newStock = data.getExtras().getParcelable(StockMessage);



            update(newStock);

        }
        else if (requestCode == RESULT_CANCELED){
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

        update(newStock);
    }

    // Update is used to set text and image after a restart of the app, or a new stock is created
    private void update( Stock stock){

        Resources res = getResources();

//        if (stock == null) {
//            Stock defaultStock = new Stock("N/A", 00, 0, "N/A");
//            nameText.setText(defaultStock.getStockName());
//
//            priceText.setText(String.format(res.getString(R.string.StockPrice), defaultStock.getStockPrice()));
//
//            sectorImage.setImageResource(R.drawable.na);
//        }
//        else {
//
//            priceText.setText(String.format(res.getString(R.string.StockPrice), stock.getStockPrice()));
//
//            nameText.setText(stock.getStockName());
//
//            if (stock.getStockSector().equals(getString(R.string.CheckBox1))) // Tech
//                sectorImage.setImageResource(R.drawable.tech);
//            else if (stock.getStockSector().equals(getString(R.string.CheckBox2))) // health
//                sectorImage.setImageResource(R.drawable.health);
//            else if (stock.getStockSector().equals(getString(R.string.CheckBox3))) // Materials
//                sectorImage.setImageResource(R.drawable.materials);
//            else
//                sectorImage.setImageResource(R.drawable.na);
//        }
    }

    private void goToDetailsActivity(){
        Intent intent = new Intent(OverviewActivity.this, DetailsActivity.class);
        intent.putExtra(StockMessage, newStock);
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
}
