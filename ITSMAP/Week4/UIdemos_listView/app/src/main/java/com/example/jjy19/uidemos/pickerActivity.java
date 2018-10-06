package com.example.jjy19.uidemos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

public class pickerActivity extends AppCompatActivity {

    NumberPicker firstNumber, secondNumber, thirdNumber;
    Toast mToast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picker);

        firstNumber = findViewById(R.id.firstNumber);
        secondNumber = findViewById(R.id.secondNumber);
        thirdNumber = findViewById(R.id.thirdNumber);

        firstNumber.setMaxValue(9);
        firstNumber.setMinValue(0);
        secondNumber.setMaxValue(9);
        secondNumber.setMinValue(0);
        thirdNumber.setMaxValue(9);
        thirdNumber.setMinValue(0);

        firstNumber.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                calculateNumber();
            }
        });

        secondNumber.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                calculateNumber();
            }
        });

        thirdNumber.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                calculateNumber();
            }
        });


    }

    private void calculateNumber(){

        int sum = firstNumber.getValue()*100 + secondNumber.getValue()*10 + thirdNumber.getValue();

        Toast(Integer.toString(sum));
    }

    private void Toast(String message){

        if(mToast != null)
        {
            mToast.cancel();
        }

        mToast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);

        mToast.show();
    }

}
