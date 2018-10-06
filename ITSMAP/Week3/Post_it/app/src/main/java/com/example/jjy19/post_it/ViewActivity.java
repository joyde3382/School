package com.example.jjy19.post_it;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ViewActivity extends AppCompatActivity {

    int requestCode = 101;

    public static final String EXTRA_MESSAGE = "Hello";
    Button editButton;
    TextView displayText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        editButton = findViewById(R.id.editBtn);

        Intent intent = getIntent();
        String message = intent.getStringExtra(ViewActivity.EXTRA_MESSAGE);

        // Capture the layout's TextView and set the string as its text
        displayText = findViewById(R.id.displayText);
        displayText.setText(message);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                goToEditActivity();

            }
        });
    }

    private void goToEditActivity(){
        Intent intent = new Intent(ViewActivity.this, EditActivity.class);
        startActivityForResult(intent,requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode == RESULT_OK){
            displayText.setText(data.getStringExtra(EXTRA_MESSAGE));
            Toast toast = Toast.makeText(getApplicationContext(), "Ok Clicked", Toast.LENGTH_SHORT);
            toast.show();
        }
        else if (resultCode == RESULT_CANCELED){
            Toast toast = Toast.makeText(getApplicationContext(), "Cancel Clicked", Toast.LENGTH_SHORT);
            toast.show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


}
