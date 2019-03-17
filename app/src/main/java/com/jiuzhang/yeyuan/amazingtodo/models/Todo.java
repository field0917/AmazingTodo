package com.jiuzhang.yeyuan.amazingtodo.models;


import android.os.Parcel;
import android.os.Parcelable;

import com.jiuzhang.yeyuan.amazingtodo.utils.DateUtils;

import java.util.Date;
import java.util.UUID;

public class Todo implements Parcelable{
    public String id;

    public String text;

    public boolean done;

    public Date remindDate;

    public Todo() {}

    public Todo(String text, Date remindDate) {
        this.id = UUID.randomUUID().toString();
        this.text = text;
        this.done = false;
        this.remindDate = remindDate;
    }

    protected Todo(Parcel in) {
        id = in.readString();
        text = in.readString();
        done = in.readByte() != 0;

        long date = in.readLong();
        remindDate = date == 0 ? null : new Date(date);
    }

    public static final Creator<Todo> CREATOR = new Creator<Todo>() {
        @Override
        public Todo createFromParcel(Parcel in) {
            return new Todo(in);
        }

        @Override
        public Todo[] newArray(int size) {
            return new Todo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(text);
        parcel.writeByte((byte) (done ? 1 : 0));
        //parcel.writeString(DateUtils.dateToString(remindDate));
        parcel.writeLong(remindDate != null ? remindDate.getTime() : 0);
    }
}
