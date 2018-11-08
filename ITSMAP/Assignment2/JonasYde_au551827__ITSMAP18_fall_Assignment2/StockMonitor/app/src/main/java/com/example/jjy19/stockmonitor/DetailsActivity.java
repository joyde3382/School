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
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.jjy19.stockmonitor.Objects.Stock;
import com.example.jjy19.stockmonitor.Service.StockService;

import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    Button editButton, backButton, deleteButton;
    TextView nameText, priceText, sellText, stockText, sectorText, timeStampText, exchangeText;

    Stock newStock;
    private StockService boundService;
    ServiceConnection myServiceConnection;
    SharedVariables sV;
    List<Stock> stocks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // initialize StockService
        initServiceConnection();

        // initialize UI elements
        initElements();

        // setup text with values from saved object
        setDetailsText(newStock);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToEditActivity();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailsActivity.this, OverviewActivity.class);

                boundService.deleteStock(newStock);
                intent.putExtra("requestCode", SharedVariables.requestCodes.Delete.getValue());
                setResult(RESULT_OK, intent);

                finish();
            }
        });
    }

    // broadcastReciver, handling broadcasts from StockService
    public BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {

                stocks = intent.getParcelableArrayListExtra("ServiceStockList");
                final int stockListSize = stocks.size();

                // only update stock if it has new data (price)
                for (int i = 0; i < stockListSize; i++) {

                    Stock tempStock = stocks.get(i);

                    if(tempStock.getSid() == newStock.getSid())
                    {
                        if(tempStock.getLatestValue() != newStock.getLatestValue()){
                            newStock = tempStock;
                            setDetailsText(newStock);
                        }

                    }

                }
            }
        }
    };


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // if result is OK save data from stock object and parse it to OverView activity
        if(resultCode == RESULT_OK) {
            Intent intent = new Intent(this, OverviewActivity.class);
            intent.putExtra("requestCode", SharedVariables.requestCodes.Update.getValue());
            setResult(RESULT_OK, intent);
            finish();
        }
        else if (requestCode == RESULT_CANCELED){
            sV.Toast("Cancel Clicked");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setDetailsText(Stock stock){


        if (stock != null) {
            nameText.setText(stock.getCompanyName());
            priceText.setText("" + stock.getStockPrice());
            sellText.setText(Double.toString(stock.getSellValue()));
            stockText.setText("" + stock.getNumberOfStocks());
            exchangeText.setText(stock.getPrimaryExchange());
            sectorText.setText(stock.getStockSector());
            timeStampText.setText(stock.getTimeStamp());

        }
        else{
            Stock defaultStock = new Stock("N/A","",00,0,"N/A");
            nameText.setText(defaultStock.getCompanyName());
            priceText.setText("" + defaultStock.getStockPrice());
            sellText.setText(Double.toString(defaultStock.getSellValue()));
            exchangeText.setText(defaultStock.getPrimaryExchange());
            stockText.setText("" + defaultStock.getNumberOfStocks());
            sectorText.setText(defaultStock.getStockSector());
            timeStampText.setText(defaultStock.getTimeStamp());
        }
    }

    private void initElements(){
        editButton = findViewById(R.id.editBtn);
        backButton = findViewById(R.id.backBtn);
        deleteButton = findViewById(R.id.deleteBtn);

        nameText = findViewById(R.id.details_name);
        priceText = findViewById(R.id.details_price);
        sellText = findViewById(R.id.details_sell);
        exchangeText = findViewById(R.id.details_exchange);
        stockText = findViewById(R.id.details_stock);
        sectorText = findViewById(R.id.details_sector);
        timeStampText = findViewById(R.id.details_timeStamp);

        // shared variables object
        sV = (SharedVariables) getApplication();

        // setup localBroadcastMananger
        LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(this);
        lbm.registerReceiver(receiver, new IntentFilter("filter_string"));

        newStock = getIntent().getParcelableExtra(sV.StockMessage);

    }

    private void goToEditActivity(){
        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra(sV.StockMessage, newStock);
        intent.putExtra("DetailsData", "UpdateStock");
        startActivityForResult(intent, sV.requestCode);
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

    }

    @Override
    protected void onStart() {
        super.onStart();

        //bind to service
        Intent intent = new Intent(DetailsActivity.this, StockService.class);
        bindService(intent,myServiceConnection, Context.BIND_AUTO_CREATE);
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
}
