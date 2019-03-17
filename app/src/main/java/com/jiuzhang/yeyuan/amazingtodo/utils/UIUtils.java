package com.jiuzhang.yeyuan.amazingtodo.utils;


import android.graphics.Paint;
import android.widget.TextView;

public class UIUtils {
    public static void setStrikeThough (TextView tv, boolean done) {
        if (done) {
            // set strike though the text view
            tv.setPaintFlags(tv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            tv.setPaintFlags(tv.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }
}
