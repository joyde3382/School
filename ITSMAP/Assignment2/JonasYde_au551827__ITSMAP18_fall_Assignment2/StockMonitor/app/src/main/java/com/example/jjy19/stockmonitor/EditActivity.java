package com.example.jjy19.stockmonitor;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.jjy19.stockmonitor.Objects.Stock;
import com.example.jjy19.stockmonitor.Service.StockService;

public class EditActivity extends AppCompatActivity {

    Button saveButton, cancelButton;
    EditText nameText, symbolText, priceText, stockText;

    Stock newStock;

    private StockService boundService;
    ServiceConnection myServiceConnection;
    SharedVariables sV;
    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        // initialize StockService
        initServiceConnection();

        // initialize UI elements
        initElements();

        setEditText();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveStock();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    // Save data from stock to object and parse it to Details activity
    @SuppressLint("StaticFieldLeak")
    private void saveStock(){
        Intent intent = new Intent(this, OverviewActivity.class);

        Resources res = getResources();

        if(nameText.getEditableText().toString().equals(""))
        {
            sV.Toast(res.getString(R.string.MissingName));
            return;
        }
        if(symbolText.getEditableText().toString().equals(""))
        {
            sV.Toast(res.getString(R.string.MissingSymbol));
            return;
        }

        if(priceText.getEditableText().toString().equals(""))
        {
            sV.Toast(res.getString(R.string.MissingPrice));
            return;
        }

        if(stockText.getEditableText().toString().equals(""))
        {
            sV.Toast(res.getString(R.string.MissingStock));
            return;
        }


        if (newStock == null)
        {
            newStock = new Stock("N/A","",00,0,"N/A");
        }

        // Create new stock object with the input values from user
        newStock.setCompanyName(nameText.getText().toString());
        newStock.setSymbol(symbolText.getText().toString());
        newStock.setStockPrice(Double.parseDouble(priceText.getText().toString()));
        newStock.setNumberOfStocks(Integer.parseInt(stockText.getText().toString()));

        if (message.equals("UpdateStock")) {
            intent.putExtra("requestCode", SharedVariables.requestCodes.Update.getValue());
            boundService.updateStock(newStock);
        }
        else {
            intent.putExtra("requestCode", SharedVariables.requestCodes.Add.getValue());
            boundService.addStock(newStock);
        }

        setResult(RESULT_OK, intent);

        finish();

    }

    private void setEditText(){

        if (newStock != null) {
            nameText.setText(newStock.getCompanyName());
            symbolText.setText(newStock.getSymbol());
            priceText.setText("" + newStock.getStockPrice());
            stockText.setText("" + newStock.getNumberOfStocks());

            if (message.equals("UpdateStock"))
            {
                nameText.setEnabled(false);
                symbolText.setEnabled(false);
            }
        }

        // if adding a new stock, use deault values
        else{
            Stock defaultStock = new Stock("N/A","",00,0,"N/A");
            nameText.setText(defaultStock.getCompanyName());
            symbolText.setText(defaultStock.getSymbol());
            priceText.setText("" + defaultStock.getStockPrice());
            stockText.setText("" + defaultStock.getNumberOfStocks());

            if (message.equals("UpdateStock"))
            {
                nameText.setEnabled(false);
                symbolText.setEnabled(false);
            }
        }
    }

    private void initElements(){

        // buttons
        saveButton = findViewById(R.id.saveBtn);
        cancelButton = findViewById(R.id.backBtn);

        // editText
        nameText = findViewById(R.id.edit_name);
        symbolText = findViewById(R.id.edit_symbol);
        priceText = findViewById(R.id.edit_price);
        stockText = findViewById(R.id.edit_stock);

        sV = (SharedVariables) getApplication();

        // get messages from other activities
        newStock = getIntent().getParcelableExtra(sV.StockMessage);
        message = getIntent().getStringExtra("DetailsData");

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
        Intent intent = new Intent(EditActivity.this, StockService.class);
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
}
