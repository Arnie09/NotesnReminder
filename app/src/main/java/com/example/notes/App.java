package com.example.notes;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class App extends Application {
    public static final String CHANNEL_ID = "Channel1";

    @Override
    public void onCreate(){
        super.onCreate();
        createNotificationChannels();
    }
    //this method actually creates the the notification channel for our application
    public void createNotificationChannels(){
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_ID,
                    "New Reminder",
                    NotificationManager.IMPORTANCE_HIGH
            );
            //text to display in the notification
            channel1.setDescription("You have a reminder");
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
        }
    }
}

