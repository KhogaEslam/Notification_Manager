package com.khoga.notificationmanager;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Method;

public class CreateNotification extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void createNotification(View view) {
        Notification notification = null;
        String contentTitle = "This is the Title";
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        //Hide notification after it is selected
        Intent intent = new Intent(this, NotificationReceiver.class);

        PendingIntent activity = PendingIntent.getActivity(this, 0, intent, 0);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            notification = new Notification(R.drawable.icon, "A new notification", System.currentTimeMillis());
            notification.flags |= Notification.FLAG_AUTO_CANCEL;

            try {
                Method deprecatedMethod = notification.getClass().getMethod("setLatestEventInfo", Context.class, CharSequence.class, CharSequence.class, PendingIntent.class);
                deprecatedMethod.invoke(notification, getApplicationContext(), contentTitle, null, activity);
            } catch (Exception e) {
                Log.w("TAG", "Method not found", e);
            }
        }
        else{
            // Use new API
            Notification.Builder builder = new Notification.Builder(getApplicationContext())
                    .setContentIntent(activity)
                    .setSmallIcon(R.drawable.icon)
                    .setContentTitle(contentTitle);
            notification.flags |= Notification.FLAG_AUTO_CANCEL;
            notification = builder.build();
        }
    }
}
