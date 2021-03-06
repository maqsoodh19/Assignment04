package com.example.maqso.assignment04;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

public class CounterService extends Service {

    Boolean isRunning = false;

    public CounterService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        isRunning = true;
        final int limit = intent.getIntExtra("limit", 0);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= limit && isRunning; i++) {
                    try {
                        EventBus.getDefault().post(new MyEvents(i));
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        isRunning = false;
        super.onDestroy();
    }
}
