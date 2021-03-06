package com.example.notes;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.Toast;

import static com.example.notes.App.CHANNEL_ID;

public class AlarmReceiver extends BroadcastReceiver {
    DatabaseHandler databaseHandler;
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("AlarmReceiver:","Here");

        String topic = intent.getStringExtra("Message");
        String id = intent.getStringExtra("ID");

        //Toast.makeText(context, "Alarm received!"+topic, Toast.LENGTH_LONG).show();
        Log.i("AlarmReceiver:",topic);
        Log.i("AlarmReceiver:",String.valueOf(id));
        databaseHandler = new DatabaseHandler(context);
        databaseHandler.deleteData(String.valueOf(id));



        /* creating pending intent because the intent creation will take place in the future */
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,new Intent(),PendingIntent.FLAG_ONE_SHOT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        Notification notification = new NotificationCompat.Builder(context,CHANNEL_ID)
                .setSmallIcon(R.drawable.notification)
                .setContentTitle("Reminder")
                .setContentText(topic)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_EVENT)
                .setAutoCancel(true)
                .addAction(R.drawable.ic_check_black_24dp,"Okay",pendingIntent)
                .build();
        notificationManager.notify(1,notification);
        Log.i("AlarmReceiver:","After Notification Manager");
    }
}