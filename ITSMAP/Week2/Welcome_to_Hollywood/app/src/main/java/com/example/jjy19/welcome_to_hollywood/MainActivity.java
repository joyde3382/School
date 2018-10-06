package com.example.jjy19.welcome_to_hollywood;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Main Activity", "onStart called!");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Main Activity", "onResume called!");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Main Activity", "onPause called!");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Main Activity", "onStop called!");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Main Activity", "onDestroy called!");
    }
}
