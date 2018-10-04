package com.example.jjy19.stockmonitor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DetailsActivity extends AppCompatActivity {

    private static final String StockMessage = "stock";
    int requestCode = 101;
    Button editButton;
    Button backButton;

    TextView nameText;
    TextView priceText;
    TextView stockText;
    TextView sectorText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        editButton = findViewById(R.id.editBtn);
        backButton = findViewById(R.id.backBtn);

        nameText = findViewById(R.id.details_name);
        priceText = findViewById(R.id.details_price);
        stockText = findViewById(R.id.details_stock);
        sectorText = findViewById(R.id.details_sector);

        Stock newStock = getIntent().getParcelableExtra(StockMessage);

        if (newStock != null) {
            nameText.setText(newStock.getStockName());
            priceText.setText("" + newStock.getStockPrice());
            stockText.setText("" + newStock.getNumberOfStocks());
            sectorText.setText(newStock.getStockSector());
        }
        else{
            Stock defaultStock = new Stock("N/A",00,0,"N/A");
            nameText.setText(defaultStock.getStockName());
            priceText.setText("" + defaultStock.getStockPrice());
            stockText.setText("" + defaultStock.getNumberOfStocks());
            sectorText.setText(defaultStock.getStockSector());
        }

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
    }

    private void goToEditActivity(){
        Intent intent = new Intent(this, EditActivity.class);
        startActivityForResult(intent,requestCode);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode == RESULT_OK) {
            Intent intent = new Intent(this, OverviewActivity.class);
            Stock newStock = data.getExtras().getParcelable(StockMessage);
            intent.putExtra(StockMessage,newStock);
            setResult(RESULT_OK, intent);
            finish();
        }
        else if (requestCode == RESULT_CANCELED){
            Toast("Cancel Clicked");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private  void Toast(String message){
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG);
        toast.show();
    }
}
