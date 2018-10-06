package com.example.jjy19.uidemos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    int requestCode = 101;
    public static final String EXTRA_MESSAGE = "Hello";

    Button pickerButtonDemo;
    Button editButtonDemo;
    Button sliderButtonDemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pickerButtonDemo = findViewById(R.id.pickerBtn);
        editButtonDemo = findViewById(R.id.editTextBtn);
        sliderButtonDemo = findViewById(R.id.sliderBtn);

        pickerButtonDemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                goToPickerActivity();

            }
        });

        editButtonDemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                goToEditTextActivity();

            }
        });

        sliderButtonDemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                goToSliderActivity();

            }
        });

    }


    private void goToPickerActivity(){
        Intent intent = new Intent(MainActivity.this, pickerActivity.class);
        startActivityForResult(intent,requestCode);
    }

    private void goToEditTextActivity(){
        Intent intent = new Intent(MainActivity.this, editTextActivity.class);
        startActivityForResult(intent,requestCode);
    }

    private void goToSliderActivity(){
        Intent intent = new Intent(MainActivity.this, sliderActivity.class);
        startActivityForResult(intent,requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode == RESULT_OK){
            String message = data.getStringExtra(EXTRA_MESSAGE);

            Toast(message);
        }
        else if (resultCode == RESULT_CANCELED){
           Toast("Cancel Clicked");
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void Toast(String message){
        Toast toast = Toast.makeText(getApplicationContext(), "" + message, Toast.LENGTH_SHORT);
        toast.show();
    }
}
