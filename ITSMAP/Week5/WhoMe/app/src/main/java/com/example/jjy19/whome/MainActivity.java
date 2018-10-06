package com.example.jjy19.whome;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    EditText editFirstName;
    EditText editLastName;
    EditText editAge;
    EditText editPhoneNr;

    SharedPreferences sp;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editFirstName = findViewById(R.id.editFirstName);
        editLastName = findViewById(R.id.editLastName);
        editAge = findViewById(R.id.editAge);
        editPhoneNr = findViewById(R.id.editPhone);


        SharedPreferences sp = getSharedPreferences("my_prefs", Activity.MODE_PRIVATE);
        editFirstName.setText(sp.getString("firstNameKey", ""));
        editLastName.setText(sp.getString("lastNameKey", ""));
        editAge.setText(sp.getString("ageKey", ""));
        editPhoneNr.setText(sp.getString("phoneKey", ""));


    }

    @Override
    protected void onStop() {
        sp = getSharedPreferences("my_prefs", Activity.MODE_PRIVATE);
        editor = sp.edit();
        editor.putString("firstNameKey", editFirstName.getText().toString());
        editor.putString("lastNameKey", editLastName.getText().toString());
        editor.putString("ageKey", editAge.getText().toString());
        editor.putString("phoneKey", editPhoneNr.getText().toString());
        editor.apply();

        super.onStop();

    }
}
