package com.example.jjy19.illbebackground;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.os.IBinder;

public class MainActivity extends AppCompatActivity {

    Button startButton, stopButton, bindButton, unbindButton, getCountButton;
    Toast toast;
    private  myBoundService boundService;
    private ServiceConnection myServiceConnection;
    boolean bound = false;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(this);
        lbm.registerReceiver(receiver, new IntentFilter("filter_string"));


        startButton = findViewById(R.id.startServiceBtn);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyBackgroundService.class);
                startService(intent);
            }
        });

        stopButton = findViewById(R.id.stopServiceBtn);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyBackgroundService.class);
                stopService(intent);
            }
        });

        bindButton = findViewById(R.id.bindBtn);
        bindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, myBoundService.class);
                bindService(intent, myServiceConnection, Context.BIND_AUTO_CREATE);
                bound = true;
            }
        });

        unbindButton = findViewById(R.id.unbindBtn);
        unbindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bound) {
                    // Detach our existing connection.
                    unbindService(myServiceConnection);
                    bound = false;
                }
            }
        });

        getCountButton = findViewById(R.id.getCountBtn);
        getCountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bound && boundService!=null){
                    count = boundService.getCount();
                    //update textView
                    Toast("Count is " + count);
                } else {
                    Toast("Not bound yet");

                }
            }
        });

            myServiceConnection = new ServiceConnection() {
                public void onServiceConnected(ComponentName className, IBinder service) {
                    // This is called when the connection with the service has been
                    // established, giving us the service object we can use to
                    // interact with the service.  Because we have bound to a explicit
                    // service that we know is running in our own process, we can
                    // cast its IBinder to a concrete class and directly access it.
                    //ref: http://developer.android.com/reference/android/app/Service.html
                    boundService = ((myBoundService.serviceBinder) service).getService();
                    //Log.d(LOG, "Counting service connected");

                }

                @Override
                public void onServiceDisconnected(ComponentName name) {
                    boundService = null;
                }
            };
    }

    public BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                String str = intent.getStringExtra("data");

                Toast(str);
                // get all your data from intent and do what you want
            }
        }
    };

    // simple toast function
    public void Toast(String message){
        if(toast != null){
            toast.cancel();
        }
        toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }

    // Logging of the life cycles
    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Lifecycle", "onStart called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Lifecycle", "onStop() called");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("Lifecycle", "onRestart() called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Lifecycle", "onDestroy() called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Lifecycle", "onResume() called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Lifecycle", "onPause() called");
    }
}
