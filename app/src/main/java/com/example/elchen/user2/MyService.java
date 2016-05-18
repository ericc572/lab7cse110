package com.example.elchen.user2;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import com.firebase.client.Firebase;

public class MyService extends Service {
    public MyService() {
    }

    final class MyThread implements Runnable {
        int startID;
        public MyThread(int startID) {
            this.startID = startID;
        }

        public void run() {
            String [] arr = {"hello","from", "user", "2"};
            Firebase db = new Firebase("https://elchen-110.firebaseio.com/first");

            for (String s : arr) {
                try {
                    Thread.sleep(3000);
                    db.setValue(s);

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

            }


        }
    }

    public int onStartCommand(Intent intent, int flags, int startID) {
        Toast.makeText(MyService.this, "Service Started!", Toast.LENGTH_LONG).show();
        Thread thread = new Thread(new MyThread(startID));
        thread.start();
        return super.onStartCommand(intent, flags, startID);
    }

    public void onHandleIntent (Intent intent) {
        if (intent != null) {
            synchronized (this) {
                try {
                    wait(150000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                stopService(intent);
            }
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
