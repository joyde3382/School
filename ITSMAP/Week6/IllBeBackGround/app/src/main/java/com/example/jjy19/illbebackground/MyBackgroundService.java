package com.example.jjy19.illbebackground;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

public class MyBackgroundService extends Service {


    public MyBackgroundService() {
    }
    Thread worker;
    // LocalBroadcastManager myLocalBroadcast;
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("MyBackgroundService", "onCreate called");



        worker = new Thread() {
            @Override
            public void run() {
                try {
                    while(true) {
                        Log.d("MyBackgroundService", "Sleeping for 1 second");
                        Thread.sleep(1000);

                        Intent intent = new Intent("myLocalBroadcast");
                        intent.putExtra()
                        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
                    }
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        };

        worker.start();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        worker.interrupt();
        Log.d("MyBackgroundService", "onDestroy called");
    }

}

