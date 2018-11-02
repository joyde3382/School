package com.example.jjy19.stockmonitor;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.jjy19.stockmonitor.Objects.Stock;
import com.example.jjy19.stockmonitor.Service.StockService;

public class DetailsActivity extends AppCompatActivity {

    Button editButton, backButton, deleteButton;
    TextView nameText, priceText, sellText, stockText, sectorText, timeStampText;

    Stock newStock;
    private StockService boundService;
    ServiceConnection myServiceConnection;
    SharedVariables sV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
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


        // initialize UI elements
        initUIElements();

//        LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(this);
//        lbm.registerReceiver(receiver, new IntentFilter("filter_string"));

        newStock = getIntent().getParcelableExtra(sV.StockMessage);

        // setup text with values from saved object
        setDetailsText();

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

//    public BroadcastReceiver receiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            if (intent != null) {
//
//                String str = intent.getStringExtra("ServiceData");
//                RequestQueue queue = Volley.newRequestQueue(DetailsActivity.this);
//
//                if (str == "UpdateStocks") {
//
//
//                    for (int i = 0; i < stockListSize; i++) {
//
//                        if (stockViewModel.getAllStocks().getValue() != null) {
//
//                            final Stock updateStock = stockViewModel.getAllStocks().getValue().get(i);
//
//                            updateStockRequest(updateStock, queue);
//
//                        }
//                    }
//                }
//            }
//        }
//    };
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

    private void setDetailsText(){


        if (newStock != null) {
            nameText.setText(newStock.getCompanyName());
            priceText.setText("" + newStock.getStockPrice());
            sellText.setText(Double.toString(newStock.getPrimaryExchange()));
            stockText.setText("" + newStock.getNumberOfStocks());
            sectorText.setText(newStock.getStockSector());
            timeStampText.setText(newStock.getTimeStamp().toString());

        }
        else{
            Stock defaultStock = new Stock("N/A","",00,0,"N/A");
            nameText.setText(defaultStock.getCompanyName());
            priceText.setText("" + defaultStock.getStockPrice());
            sellText.setText(Double.toString(defaultStock.getPrimaryExchange()));
            stockText.setText("" + defaultStock.getNumberOfStocks());
            sectorText.setText(defaultStock.getStockSector());
            timeStampText.setText(defaultStock.getTimeStamp().toString());
        }
    }

    private void initUIElements(){
        editButton = findViewById(R.id.editBtn);
        backButton = findViewById(R.id.backBtn);
        deleteButton = findViewById(R.id.deleteBtn);

        nameText = findViewById(R.id.details_name);
        priceText = findViewById(R.id.details_price);
        sellText = findViewById(R.id.details_sell);
        stockText = findViewById(R.id.details_stock);
        sectorText = findViewById(R.id.details_sector);
        timeStampText = findViewById(R.id.details_timeStamp);

    }

    private void goToEditActivity(){
        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra(sV.StockMessage, newStock);
        intent.putExtra("DetailsData", "UpdateStock");
        startActivityForResult(intent, sV.requestCode);
    }

    @Override
    protected void onStart() {
        super.onStart();

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
