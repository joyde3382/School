package com.example.jjy19.stockmonitor;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class OverviewActivity extends AppCompatActivity {

    private static final String StockMessage = "stock";
    int requestCode = 101;

    Button detailsButton;
    TextView nameText;
    TextView priceText;
    Stock newStock;
    ImageView sectorImage;
    String languageToLoad;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        nameText = findViewById(R.id.overView_stockName);
        priceText = findViewById(R.id.overView_stockPrice);
        sectorImage = findViewById(R.id.overView_sectorPic);


        newStock = getIntent().getParcelableExtra(StockMessage);
        update(newStock);

        detailsButton = findViewById(R.id.detailsBtn);
        detailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToDetailsActivity();
            }
        });
    }

    private void goToDetailsActivity(){
        Intent intent = new Intent(OverviewActivity.this, DetailsActivity.class);
        intent.putExtra(StockMessage, newStock);
        startActivityForResult(intent,requestCode);
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

    private void update( Stock stock){

        String curLanguage = Locale.getDefault().getDisplayLanguage();
        Resources res = getResources();

        if (stock == null) {
            Stock defaultStock = new Stock("N/A", 00, 0, "N/A");
            nameText.setText(defaultStock.getStockName());



            priceText.setText(String.format(res.getString(R.string.StockPrice), defaultStock.getStockPrice()));

//            if (curLanguage.equals("dansk"))
//                priceText.setText(String.format("Købt for: %.1f", defaultStock.getStockPrice()));
//            else if (curLanguage.equals("English"))
//                priceText.setText(String.format(res.getString(R.string.StockPrice), defaultStock.getStockPrice()));

            sectorImage.setImageResource(R.drawable.na);
        }
        else {

            priceText.setText(String.format(res.getString(R.string.StockPrice), stock.getStockPrice()));

//            if (curLanguage.equals("dansk"))
//                priceText.setText(String.format ("Købt for: %.1f", stock.getStockPrice()));
//            else if(curLanguage.equals("English"))
//                priceText.setText(String.format ("Purchased at: %.1f", stock.getStockPrice()));

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

    private  void Toast(String message){
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }
}
