package com.example.jjy19.stockmonitor;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.jjy19.stockmonitor.Objects.Stock;

public class EditActivity extends SharedVariables {

    String sectorValue;
    Button saveButton, cancelButton;
    EditText nameText, symbolText, priceText, stockText;
    RadioButton radioButton1, radioButton2, radioButton3;
    RadioGroup sectorGroup;

    Stock newStock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        // initialize UI elements
        initUIElements();

        newStock = getIntent().getParcelableExtra(StockMessage);

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
            Toast(res.getString(R.string.MissingName));
            return;
        }
        if(symbolText.getEditableText().toString().equals(""))
        {
            Toast(res.getString(R.string.MissingSymbol));
            return;
        }

        if(priceText.getEditableText().toString().equals(""))
        {
            Toast(res.getString(R.string.MissingPrice));
            return;
        }

        if(stockText.getEditableText().toString().equals(""))
        {
            Toast(res.getString(R.string.MissingStock));
            return;
        }

        if (radioButton1.isChecked())
            sectorValue = sectors.Technology.getValue();
        else if (radioButton2.isChecked())
            sectorValue = sectors.HealthCare.getValue();
        else if (radioButton3.isChecked())
            sectorValue = sectors.BasicMaterials.getValue();
        else {
            Toast("Please pick a sector you slacker!");
            return;
        }

        // Create new stock object with the input values from user
        newStock.setCompanyName(nameText.getText().toString());
        newStock.setSymbol(symbolText.getText().toString());
        newStock.setStockPrice(Double.parseDouble(priceText.getText().toString()));
        newStock.setNumberOfStocks(Integer.parseInt(stockText.getText().toString()));
        newStock.setStockSector(sectorValue);
//        Stock stock = new Stock(nameText.getText().toString(),symbolText.getText().toString(), Double.parseDouble(priceText.getText().toString()), Integer.parseInt(stockText.getText().toString()), sectorValue);
//        stock.setSymbol(symbolText.getText().toString());

        String message = getIntent().getStringExtra("DetailsData");

        if (message.equals("UpdateStock")) {
            intent.putExtra("requestCode", requestCodes.Update.getValue());
        }
        else {
            intent.putExtra("requestCode", requestCodes.Add.getValue());
        }

        intent.putExtra(StockMessage,newStock);
        setResult(RESULT_OK, intent);

        finish();

    }

    private void setEditText(){


        if (newStock != null) {
            nameText.setText(newStock.getCompanyName());
            symbolText.setText(newStock.getSymbol());
            priceText.setText("" + newStock.getStockPrice());
            stockText.setText("" + newStock.getNumberOfStocks());

            if (newStock.getStockSector() == sectors.Technology.getValue())
                sectorGroup.check(R.id.sector_radio1);
            else if (newStock.getStockSector() == sectors.HealthCare.getValue())
                sectorGroup.check(R.id.sector_radio2);
            else if (newStock.getStockSector() == sectors.BasicMaterials.getValue())
                sectorGroup.check(R.id.sector_radio3);

        }
        else{
            Stock defaultStock = new Stock("N/A","",00,0,"N/A");
            nameText.setText(defaultStock.getCompanyName());
            symbolText.setText(defaultStock.getSymbol());
            priceText.setText("" + defaultStock.getStockPrice());
            stockText.setText("" + defaultStock.getNumberOfStocks());

            if (defaultStock.getStockSector() == sectors.Technology.getValue())
                sectorGroup.check(R.id.sector_radio1);
            else if (defaultStock.getStockSector() == sectors.HealthCare.getValue())
                sectorGroup.check(R.id.sector_radio2);
            else if (defaultStock.getStockSector() == sectors.BasicMaterials.getValue())
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


}
