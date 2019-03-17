package com.jiuzhang.yeyuan.amazingtodo;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.jiuzhang.yeyuan.amazingtodo.models.Todo;

public class AlarmReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        final int notificationId = 100; // this will be used to cancel the notification
        final String channelId = "todo_reminder";
        Todo todo = intent.getParcelableExtra(TodoEditActivity.KEY_TODO);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId).
                setSmallIcon(R.drawable.ic_notifications_black_24dp).
                setContentTitle(todo.text).
                setContentText(todo.text);

        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(context, TodoEditActivity.class);
        resultIntent.putExtra(TodoEditActivity.KEY_TODO, todo);
        resultIntent.putExtra(TodoEditActivity.KEY_NOTIFICATION_ID, notificationId);

        PendingIntent resultPendingIntent = PendingIntent.getActivity(context,
                                                                     0,
                                                                      resultIntent,
                                                                      PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);

        NotificationManager nm = (NotificationManager) context.getSystemService(
                Context.NOTIFICATION_SERVICE);
        // notificationId allows you to update the notification later on, like canceling it
        nm.notify(notificationId, builder.build());

    }
}
