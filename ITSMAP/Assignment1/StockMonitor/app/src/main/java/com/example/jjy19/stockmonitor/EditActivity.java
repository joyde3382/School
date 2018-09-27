package com.example.jjy19.stockmonitor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {

    private static final String StockMessage = "stock";
    String sectorValue;

    Button saveButton;
    Button cancelButton;

    EditText nameText;
    EditText priceText;
    EditText stockText;
    EditText sectorText;

    CheckBox checkBox1;
    CheckBox checkBox2;
    CheckBox checkBox3;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        saveButton = findViewById(R.id.saveBtn);
        cancelButton = findViewById(R.id.backBtn);

        nameText = findViewById(R.id.edit_name);
        priceText = findViewById(R.id.edit_price);
        stockText = findViewById(R.id.edit_stock);
        // sectorText = findViewById(R.id.edit_sector);

        checkBox1 = findViewById(R.id.sector_check1);
        checkBox2 = findViewById(R.id.sector_check2);
        checkBox3 = findViewById(R.id.sector_check3);

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

        checkBox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBox2.setChecked(false);
                checkBox3.setChecked(false);
            }
        });

        checkBox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBox1.setChecked(false);
                checkBox3.setChecked(false);
            }
        });

        checkBox3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBox1.setChecked(false);
                checkBox2.setChecked(false);
            }
        });



    }

    private void saveStock(){
        Intent intent = new Intent(this, OverviewActivity.class);



        if (checkBox1.isChecked())
            sectorValue = getString(R.string.CheckBox1);
        else if (checkBox2.isChecked())
            sectorValue = getString(R.string.CheckBox2);
        else if (checkBox3.isChecked())
            sectorValue = getString(R.string.CheckBox3);
        else {
            Toast("Please pick a sector you slacker!");
            return;
        }
        Stock stock = new Stock(nameText.getText().toString(), Double.parseDouble(priceText.getText().toString()), Integer.parseInt(stockText.getText().toString()), sectorValue);

        intent.putExtra(StockMessage,stock);

        setResult(RESULT_OK, intent);

        finish();

    }

    private  void Toast(String message){
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG);
        toast.show();
    }
}
