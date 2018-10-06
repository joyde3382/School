package com.example.jjy19.rockpaperscissor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Random rand = new Random();

        final boolean[] playerOneDone = {false};
        final boolean[] playerTwoDone = {false};

        final String[] temp1 = {"roll baby"};
        final String[] temp2 = {"roll baby"};
        // Text variables
        final TextView playerOneText = (TextView) findViewById(R.id.text1);
        final TextView playerTwoText = (TextView) findViewById(R.id.text2);
        final TextView winnerText = (TextView) findViewById(R.id.winnerText);


        // button variables
        Button playerOneRoll = (Button) findViewById(R.id.roll1);
        Button playerTwoRoll = (Button) findViewById(R.id.roll2);

        // on click listeners

        // changeTextButton
        playerOneRoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //int n = rand.nextInt(2);
                int n = (int )(Math.random() * 3);


                switch (n)
                {
                    case 0:
                        temp1[0] = "Paper";
                        break;
                    case 1:
                        temp1[0] = "Rock";
                        break;
                    case 2:
                        temp1[0] = "Scissor";
                        break;
                }
                playerOneText.setText(temp1[0]);

                playerOneDone[0] = true;

                if (playerOneDone[0] && playerTwoDone[0])
                {
                    winnerText.setText("Lol too much work");
                }

            }
        });

        playerTwoRoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int n = rand.nextInt(3);

                switch (n)
                {
                    case 0:
                        temp2[0] = "Paper";
                        break;
                    case 1:
                        temp2[0] = "Rock";
                        break;
                    case 2:
                        temp2[0] = "Scissor";
                        break;

                    default:
                        break;
                }
                playerTwoText.setText(temp2[0]);

                playerTwoDone[0] = true;

                if (playerOneDone[0] && playerTwoDone[0])
                {
                    winnerText.setText("Lol too much work");
                }
            }
        });


    }
}
