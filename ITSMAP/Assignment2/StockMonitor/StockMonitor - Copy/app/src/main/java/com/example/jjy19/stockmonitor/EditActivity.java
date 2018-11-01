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
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.jjy19.stockmonitor.Objects.Stock;
import com.example.jjy19.stockmonitor.Service.StockService;

public class EditActivity extends AppCompatActivity {

    String sectorValue;
    Button saveButton, cancelButton;
    EditText nameText, symbolText, priceText, stockText;
    RadioButton radioButton1, radioButton2, radioButton3;
    RadioGroup sectorGroup;

    Stock newStock;

    private StockService boundService;
    ServiceConnection myServiceConnection;
    SharedVariables sV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

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

        newStock = getIntent().getParcelableExtra(sV.StockMessage);

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

        if (radioButton1.isChecked())
            sectorValue = SharedVariables.sectors.Technology.getValue();
        else if (radioButton2.isChecked())
            sectorValue = SharedVariables.sectors.HealthCare.getValue();
        else if (radioButton3.isChecked())
            sectorValue = SharedVariables.sectors.BasicMaterials.getValue();
        else {
            sV.Toast("Please pick a sector you slacker!");
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
        newStock.setStockSector(sectorValue);

        String message = getIntent().getStringExtra("DetailsData");

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

            if (newStock.getStockSector() == SharedVariables.sectors.Technology.getValue())
                sectorGroup.check(R.id.sector_radio1);
            else if (newStock.getStockSector() == SharedVariables.sectors.HealthCare.getValue())
                sectorGroup.check(R.id.sector_radio2);
            else if (newStock.getStockSector() == SharedVariables.sectors.BasicMaterials.getValue())
                sectorGroup.check(R.id.sector_radio3);

        }
        else{
            Stock defaultStock = new Stock("N/A","",00,0,"N/A");
            nameText.setText(defaultStock.getCompanyName());
            symbolText.setText(defaultStock.getSymbol());
            priceText.setText("" + defaultStock.getStockPrice());
            stockText.setText("" + defaultStock.getNumberOfStocks());

            if (defaultStock.getStockSector() == SharedVariables.sectors.Technology.getValue())
                sectorGroup.check(R.id.sector_radio1);
            else if (defaultStock.getStockSector() == SharedVariables.sectors.HealthCare.getValue())
                sectorGroup.check(R.id.sector_radio2);
            else if (defaultStock.getStockSector() == SharedVariables.sectors.BasicMaterials.getValue())
                sectorGroup.check(R.id.sector_radio3);
        }
    }

    private void initUIElements(){

        // buttons
        saveButton = findViewById(R.id.saveBtn);
        cancelButton = findViewById(R.id.backBtn);

        // editText
        nameText = findViewById(R.id.edit_name);
        symbolText = findViewById(R.id.edit_symbol);
        priceText = findViewById(R.id.edit_price);
        stockText = findViewById(R.id.edit_stock);

        // Radiogroup
        sectorGroup = findViewById(R.id.edit_RadioGroup);
        radioButton1 = findViewById(R.id.sector_radio1);
        radioButton2 = findViewById(R.id.sector_radio2);
        radioButton3 = findViewById(R.id.sector_radio3);
    }

    @Override
    protected void onStart() {
        super.onStart();

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
