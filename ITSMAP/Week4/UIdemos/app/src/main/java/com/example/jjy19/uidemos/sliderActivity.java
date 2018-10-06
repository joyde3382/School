package com.example.jjy19.uidemos;

import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

public class sliderActivity extends AppCompatActivity {

    SeekBar greenSlider, redSlider, blueSlider;
    ConstraintLayout layout1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);

        greenSlider = findViewById(R.id.greenSlider); // initiate the Seek bar
        redSlider = findViewById(R.id.redSlider);
        blueSlider = findViewById(R.id.blueSlider);
        //layout1 = findViewById(R.id.sliderLayout);
        layout1 = findViewById(R.id.constraintLayout);

        greenSlider.setMax(255);
        redSlider.setMax(255);
        blueSlider.setMax(255);

        greenSlider.setOnSeekBarChangeListener(ChangeColor);
        redSlider.setOnSeekBarChangeListener(ChangeColor);
        blueSlider.setOnSeekBarChangeListener(ChangeColor);
    }
    private SeekBar.OnSeekBarChangeListener ChangeColor=new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {


        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {


        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
            int R,G,B;

            R = redSlider.getProgress();
            G = greenSlider.getProgress();
            B = blueSlider.getProgress();
            layout1.setBackgroundColor(Color.rgb(R,G,B));

        }
    };
}
