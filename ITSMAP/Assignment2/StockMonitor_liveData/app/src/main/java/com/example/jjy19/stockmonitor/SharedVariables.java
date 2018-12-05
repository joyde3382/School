package com.example.jjy19.stockmonitor;


import android.app.Application;
import android.widget.Toast;

public class SharedVariables extends Application {

    // stock key for parsing stock objects
    final String StockMessage = "stock";

    Toast toast;


    public boolean bound = false;
    // default request code
    int requestCode = 101;

    enum sectors {

        Technology("Technology"),
        HealthCare("Healthcare"),
        BasicMaterials("Basic Materials");


        private final String value;

        sectors(final String newValue) {
            value = newValue;
        }

        public String getValue() { return value; }
    }

    enum requestCodes {

        Delete(102),
        Add(103),
        Update(104);


        private final int value;

        requestCodes(final int newValue) {
            value = newValue;
        }

        public int getValue() { return value; }
    }



    // simple toast function
    public void Toast(String message){
        if(toast != null){
            toast.cancel();
        }
        toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }
}
