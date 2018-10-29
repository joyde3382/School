package com.example.jjy19.stockmonitor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.jjy19.stockmonitor.Objects.Stock;
import com.example.jjy19.stockmonitor.Service.StockService;

public class DetailsActivity extends SharedVariables {

    Button editButton, backButton, deleteButton;
    TextView nameText, priceText, stockText, sectorText;

    Stock newStock;
    StockService tempService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // initialize UI elements
        initUIElements();

        newStock = getIntent().getParcelableExtra(StockMessage);

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
                intent.putExtra(StockMessage, newStock);
                intent.putExtra("requestCode", requestCodes.Delete.getValue());
                setResult(RESULT_OK, intent);


                finish();
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // if result is OK save data from stock object and parse it to OverView activity
        if(resultCode == RESULT_OK) {
            Intent intent = new Intent(this, OverviewActivity.class);
            Stock newStock = data.getExtras().getParcelable(StockMessage);
            intent.putExtra(StockMessage,newStock);
            intent.putExtra("requestCode", data.getIntExtra("requestCode", 10));
            setResult(RESULT_OK, intent);
            finish();
        }
        else if (requestCode == RESULT_CANCELED){
            Toast("Cancel Clicked");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setDetailsText(){


        if (newStock != null) {
            nameText.setText(newStock.getCompanyName());
            priceText.setText("" + newStock.getStockPrice());
            stockText.setText("" + newStock.getNumberOfStocks());
            sectorText.setText(newStock.getStockSector());
        }
        else{
            Stock defaultStock = new Stock("N/A","",00,0,"N/A");
            nameText.setText(defaultStock.getCompanyName());
            priceText.setText("" + defaultStock.getStockPrice());
            stockText.setText("" + defaultStock.getNumberOfStocks());
            sectorText.setText(defaultStock.getStockSector());
        }
    }

    private void initUIElements(){
        editButton = findViewById(R.id.editBtn);
        backButton = findViewById(R.id.backBtn);
        deleteButton = findViewById(R.id.deleteBtn);

        nameText = findViewById(R.id.details_name);
        priceText = findViewById(R.id.details_price);
        stockText = findViewById(R.id.details_stock);
        sectorText = findViewById(R.id.details_sector);
    }

    private void goToEditActivity(){
        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra(StockMessage, newStock);
        intent.putExtra("DetailsData", "UpdateStock");
        startActivityForResult(intent,requestCode);
    }
}
