package com.example.jjy19.post_it;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "Hello";
    Button okButton;
    Button cancelButton;
    EditText inputText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        okButton = findViewById(R.id.okBtn);
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

        Intent intent = new Intent(this, ViewActivity.class);
        inputText = findViewById(R.id.inputText);
        String message = inputText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        //startActivity(intent);

        setResult(RESULT_OK, intent);


        finish();
    }

    private void buttonCancelSend(){

        Intent intent = new Intent(this, ViewActivity.class);
        intent.putExtra(EXTRA_MESSAGE, "Cancelled");
        setResult(RESULT_CANCELED, intent);

        finish();
    }

}
