package com.example.jjy19.lab3_there_and_back_again;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button buttonMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonMain = findViewById(R.id.fowardBtn);

        buttonMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                buttonFun();
            }
        });
    }

    private void buttonFun(){

        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        startActivity(intent);
    }


}
