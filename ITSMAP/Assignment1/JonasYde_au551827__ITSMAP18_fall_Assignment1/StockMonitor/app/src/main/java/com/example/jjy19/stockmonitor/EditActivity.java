package com.example.jjy19.stockmonitor;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {

    private static final String StockMessage = "stock";
    String sectorValue;

    Button saveButton;
    Button cancelButton;

    EditText nameText;
    EditText priceText;
    EditText stockText;

    RadioButton radioButton1;
    RadioButton radioButton2;
    RadioButton radioButton3;



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

        radioButton1 = findViewById(R.id.sector_radio1);
        radioButton2 = findViewById(R.id.sector_radio2);
        radioButton3 = findViewById(R.id.sector_radio3);

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

    private void saveStock(){
        Intent intent = new Intent(this, OverviewActivity.class);

        Resources res = getResources();

        if(nameText.getEditableText().toString().equals(""))
        {
            Toast(res.getString(R.string.MissingName));
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
            sectorValue = getString(R.string.CheckBox1);
        else if (radioButton2.isChecked())
            sectorValue = getString(R.string.CheckBox2);
        else if (radioButton3.isChecked())
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
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }
}
