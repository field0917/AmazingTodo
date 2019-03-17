package com.jiuzhang.yeyuan.amazingtodo.utils;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.jiuzhang.yeyuan.amazingtodo.AlarmReceiver;
import com.jiuzhang.yeyuan.amazingtodo.TodoEditActivity;
import com.jiuzhang.yeyuan.amazingtodo.models.Todo;

import java.util.Calendar;
import java.util.Date;

public class AlarmUtils {
    public static void setAlarm(@NonNull Context context, @NonNull Todo todo) {
        Calendar c = Calendar.getInstance(); // get the current time
        if (todo.remindDate.compareTo(c.getTime()) < 0) { // this statement checks if date is smaller than current time
            // only fire alarm when date is in the future
            return;
        }
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(TodoEditActivity.KEY_TODO, todo);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context,
                                                              0,
                                                               intent,
                                                               PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, // wake up the device
                         todo.remindDate.getTime(),
                         alarmIntent);
    }
}
