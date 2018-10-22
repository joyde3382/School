package com.example.jjy19.illbebackground;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

public class MyBackgroundService extends Service {

    public MyBackgroundService() {
    }
    Context context = this;
    Thread worker;
    private AsyncTask mMyTask;

    // LocalBroadcastManager myLocalBroadcast;
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("MyBackgroundService", "onCreate called");
        mMyTask = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {

                    while (true) {
                        Log.d("MyBackgroundService", "Sleeping for 1 second");
                        Thread.sleep(1000);

                        Intent intent = new Intent();
                        intent.setAction("filter_string");
                        intent.putExtra("data", "Notice me senpai!");

                        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

                        // Intent intent = new Intent("myLocalBroadcast");
                        // intent.putExtra()
                        // LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
                    }
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return null;
            }

        }.execute();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMyTask.cancel(true);
        Log.d("MyBackgroundService", "onDestroy called");
    }

}

