package com.example.jjy19.uidemos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class editTextActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "Hello";
    public static final String RESULT_STRING = "kk";

    Button okButton;
    Button cancelButton;
    EditText nameText;
    EditText emailText;
    EditText numberText;
    EditText passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text);

        nameText = findViewById(R.id.editName);
        emailText = findViewById(R.id.editEmail);
        numberText = findViewById(R.id.editNumber);
        passwordText = findViewById(R.id.editPassWord);

        okButton = findViewById(R.id.OkBtn);
        cancelButton = findViewById(R.id.cancelBtn);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                buttonOkSend();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                buttonCancelSend();
            }
        });

    }

    private void buttonOkSend(){

        Intent intent = new Intent(this, MainActivity.class);

        String message = nameText.getText().toString() + "\n" + emailText.getText().toString() + "\n" + numberText.getText().toString() + "\n" + passwordText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);

        setResult(RESULT_OK, intent);

        finish();
    }

    private void buttonCancelSend(){

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(EXTRA_MESSAGE, "Cancelled");
        setResult(RESULT_CANCELED, intent);

        finish();
    }
}
