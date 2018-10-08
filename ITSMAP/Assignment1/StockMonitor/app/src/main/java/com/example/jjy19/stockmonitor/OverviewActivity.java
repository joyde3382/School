package com.example.jjy19.stockmonitor;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jjy19.stockmonitor.Objects.Stock;

public class OverviewActivity extends SharedVariables {

    Button detailsButton;
    TextView nameText, priceText;
    Stock newStock;
    ImageView sectorImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        // initialize ui elements
        initUIElements();

        newStock = getIntent().getParcelableExtra(StockMessage);

        // update the stock object (if phone is flipped/app is restarted)
        update(newStock);

        detailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToDetailsActivity();
            }
        });
    }


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

        if (stock == null) {
            Stock defaultStock = new Stock("N/A", 00, 0, "N/A");
            nameText.setText(defaultStock.getStockName());

            priceText.setText(String.format(res.getString(R.string.StockPrice), defaultStock.getStockPrice()));

            sectorImage.setImageResource(R.drawable.na);
        }
        else {

            priceText.setText(String.format(res.getString(R.string.StockPrice), stock.getStockPrice()));

            nameText.setText(stock.getStockName());

            if (stock.getStockSector().equals(getString(R.string.CheckBox1))) // Tech
                sectorImage.setImageResource(R.drawable.tech);
            else if (stock.getStockSector().equals(getString(R.string.CheckBox2))) // health
                sectorImage.setImageResource(R.drawable.health);
            else if (stock.getStockSector().equals(getString(R.string.CheckBox3))) // Materials
                sectorImage.setImageResource(R.drawable.materials);
            else
                sectorImage.setImageResource(R.drawable.na);
        }
    }

    private void goToDetailsActivity(){
        Intent intent = new Intent(OverviewActivity.this, DetailsActivity.class);
        intent.putExtra(StockMessage, newStock);
        startActivityForResult(intent,requestCode);
    }

    private void initUIElements(){
        nameText = findViewById(R.id.overView_stockName);
        priceText = findViewById(R.id.overView_stockPrice);
        sectorImage = findViewById(R.id.overView_sectorPic);
        detailsButton = findViewById(R.id.detailsBtn);
    }
}
