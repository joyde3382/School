package com.example.jjy19.illbebackground;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class myBoundService extends Service {

    private int count;
    private boolean running = false;

    public class serviceBinder extends Binder {
        myBoundService getService() {
            return  myBoundService.this;
        }
    }

    private  final IBinder binder = new serviceBinder();

    public myBoundService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //note that the onCreate() is only called when service is first bound to (=started)
        //this will only run once in the services life time

        count = 0;
        running = true;

        //create a good ol' java Thread and let it sleep for a second and count up in a loop
        //first we create the runnable, then we start the thread
        Runnable r = new Runnable() {
            @Override
            public void run() {
                while(running) {
                    count++;
                    try {
                        Thread.sleep(1000); //sleep in seperate Threads - not the classroom!! :)
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread t = new Thread(r);
        t.start();
    }

    @Override
    //very important! return your IBinder (your custom Binder)
    public IBinder onBind(Intent intent) {
        return binder;
    }

    //simple method for returning current count
    public int getCount(){
        return count;
    }
}
