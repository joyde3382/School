package com.example.jjy19.stockmonitor;

import android.app.Activity;
import android.widget.Toast;

public abstract class SharedVariables extends Activity{

    // stock key for parsing stock objects
    final String StockMessage = "stock";

    // default request code
    int requestCode = 101;

    // simple toast function
    public void Toast(String message){
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }
}
