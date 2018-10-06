package com.example.jjy19.hello_android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Text variables
        final TextView variableText = (TextView) findViewById(R.id.textView3);

        // button variables
        Button changeText = (Button) findViewById(R.id.changeTextButton);
        Button exitApp = (Button) findViewById(R.id.exitButton);


        // on click listeners

        // changeTextButton
        changeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                variableText.setText("Hello android");
            }
        });

        // exitApp button
        exitApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
