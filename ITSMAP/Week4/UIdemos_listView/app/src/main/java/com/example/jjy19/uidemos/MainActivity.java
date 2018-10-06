package com.example.jjy19.uidemos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    int requestCode = 101;
    public static final String EXTRA_MESSAGE = "Hello";

    Button pickerButtonDemo;
    Button editButtonDemo;
    Button sliderButtonDemo;
    ListView mListView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "onCreate: Started");

        mListView = findViewById(R.id.myList);

        Demos numberPicker = new Demos("Number Picker");
        Demos editText = new Demos("Edit Text");
        Demos slider = new Demos("Slider");

        Demos test1 = new Demos("Test");
        Demos test2 = new Demos("Test");
        Demos test3 = new Demos("Test");
        Demos test4 = new Demos("Test");
        Demos test5 = new Demos("Test");
        Demos test6 = new Demos("Test");
        Demos test7 = new Demos("Test");
        Demos test8 = new Demos("Test");
        Demos test9 = new Demos("Test");
        Demos test10 = new Demos("Test");
        Demos test11 = new Demos("Test");
        Demos test12 = new Demos("Test");
        Demos test13 = new Demos("Test");
        Demos test14 = new Demos("Test");
        Demos test15 = new Demos("Test");



        ArrayList<Demos> demoList = new ArrayList<>();
        demoList.add(numberPicker);
        demoList.add(editText);
        demoList.add(slider);

        demoList.add(test1);
        demoList.add(test2);
        demoList.add(test3);
        demoList.add(test4);
        demoList.add(test5);
        demoList.add(test6);
        demoList.add(test7);
        demoList.add(test8);
        demoList.add(test9);
        demoList.add(test10);
        demoList.add(test11);
        demoList.add(test12);
        demoList.add(test13);
        demoList.add(test14);
        demoList.add(test15);


        CustomListAdapter adapter = new CustomListAdapter(this, R.layout.adapter_view_layout,demoList);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent;
                switch (position) {
                    case 0:
                        goToPickerActivity();
                        break;

                    case 1:
                        goToEditTextActivity();
                        break;

                    case 2:
                        goToSliderActivity();
                        break;

                }
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
