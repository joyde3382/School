package com.example.jjy19.selfieswap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    boolean swapped = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button swap = (Button) findViewById(R.id.swapButton);

        // on click listeners
        // changeTextButton
        swap.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick (View v){

            update();
            swapped = !swapped;
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("SwappedKey", swapped);

        super.onSaveInstanceState(outState);
        Log.d("Main Activity", "onSaveInstanceState called!");

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        Log.d("Main Activity", "onRestoreInstanceState called!");

        swapped = savedInstanceState.getBoolean("SwappedKey");

        update();
    }
    public void update() {
        ImageView image1Var = (ImageView) findViewById(R.id.Image1);
        ImageView image2Var = (ImageView) findViewById(R.id.Image2);

        if (swapped) {
            image1Var.setImageResource(R.drawable.darthvader);
            image2Var.setImageResource(R.drawable.anakin);
        } else {
            image1Var.setImageResource(R.drawable.anakin);
            image2Var.setImageResource(R.drawable.darthvader);
        }
    }
}
