package com.jiuzhang.yeyuan.amazingtodo.utils;


import android.support.annotation.NonNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    private static DateFormat dateFormat =
            new SimpleDateFormat("yyyy MM dd HH:mm", Locale.getDefault());

    // "Wed, 09 17, 2000"
    private static DateFormat dateFormatDate =
            new SimpleDateFormat("EEE, MMM dd, yyyy", Locale.getDefault());

    // "12:59"
    private static DateFormat dateFormatTime =
            new SimpleDateFormat("HH:mm", Locale.getDefault());

    public static Date stringToDate(@NonNull String string) {
        try {
            return dateFormat.parse(string);
        } catch (ParseException e) {
            // e.printStackTrace();
            return Calendar.getInstance().getTime();
        }
    }

    public static String dateToString(@NonNull Date date) {
        return dateFormat.format(date);
    }

    public static String dateToStringDate (@NonNull Date date) {
        return dateFormatDate.format(date);
    }

    public static String dateToStringTime (@NonNull Date date) {
        return dateFormatTime.format(date);
    }

}
