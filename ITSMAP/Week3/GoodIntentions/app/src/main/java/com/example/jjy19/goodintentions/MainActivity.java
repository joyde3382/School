package com.example.jjy19.goodintentions;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button YouTubeBtn;
    Button CameraBtn;
    Button emailBtn;
    // Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        YouTubeBtn = findViewById(R.id.YouTubeBtn);
        CameraBtn = findViewById(R.id.cameraBtn);
        emailBtn = findViewById(R.id.emailBtn);

        YouTubeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                playYouTubeVideo();

            }
        });

        CameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openCamera();

            }
        });

        emailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendEmail();

            }
        });


    }

    private void playYouTubeVideo(){
        Intent openYouTube = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=kxopViU98Xo"));
        startActivity(openYouTube);
    }

    private void openCamera(){
        Intent openCamera = new Intent((MediaStore.ACTION_IMAGE_CAPTURE));
        startActivity(openCamera);
    }

    private void sendEmail(){
        Intent sendEmail = new Intent((Intent.ACTION_SENDTO));
        startActivity(sendEmail);
    }
}
