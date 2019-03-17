package com.jiuzhang.yeyuan.amazingtodo;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.jiuzhang.yeyuan.amazingtodo.models.Todo;
import com.jiuzhang.yeyuan.amazingtodo.utils.AlarmUtils;
import com.jiuzhang.yeyuan.amazingtodo.utils.UIUtils;
import com.jiuzhang.yeyuan.amazingtodo.utils.DateUtils;

import java.util.Calendar;
import java.util.Date;

public class TodoEditActivity extends AppCompatActivity implements
        DatePickerDialog.OnDateSetListener,
        TimePickerDialog.OnTimeSetListener {

    public static final String KEY_TODO = "todo_edit";
    public static final String KEY_TODO_ID = "todo_edit_id";
    public static final String KEY_NOTIFICATION_ID = "notification_id";


    private Todo todo;
    private Date remindDate;

    private EditText todoEditView;
    private TextView dateView;
    private TextView timeView;
    private CheckBox completedCb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        todo = getIntent().getParcelableExtra(KEY_TODO);
        remindDate = todo == null ? null : todo.remindDate;
        setupUI();
        cancelNotificationIfNeeded();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void cancelNotificationIfNeeded() {
        int notificationId = getIntent().getIntExtra(KEY_NOTIFICATION_ID, -1);
        if (notificationId != -1) {
            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).cancel(notificationId);
        }
    }

    protected void setupActionBar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);
        setTitle(null); // set the title associated with this activity
    }

    protected void setupUI() {
        setContentView(R.layout.activity_todo_edit);
        setupActionBar();

        todoEditView = findViewById(R.id.todo_edit_text);
        dateView = findViewById(R.id.todo_detail_date);
        timeView = findViewById(R.id.todo_detail_time);
        completedCb = findViewById(R.id.todo_edit_complete);

        if (todo != null) {
            todoEditView.setText(todo.text);
            UIUtils.setStrikeThough(todoEditView, todo.done);
            completedCb.setChecked(todo.done);

            findViewById(R.id.todo_edit_delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    delete();
                }
            });
        } else {
            findViewById(R.id.todo_edit_delete).setVisibility(View.GONE);
        }

        if (remindDate != null) {
            dateView.setText(DateUtils.dateToStringDate(remindDate));
            timeView.setText(DateUtils.dateToStringTime(remindDate));
        } else {
            dateView.setText(R.string.set_date);
            timeView.setText(R.string.set_time);
        }

        setupDatePicker();
        setupCheckBox();
        setupSaveButton();

    }

    private void setupDatePicker() {
        dateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = getCalendarFromRemindDate();
                Dialog dialog = new DatePickerDialog(
                        TodoEditActivity.this,
                        TodoEditActivity.this,
                        c.get(Calendar.YEAR),
                        c.get(Calendar.MONTH),
                        c.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });

        timeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = getCalendarFromRemindDate();
                Dialog dialog = new TimePickerDialog(
                        TodoEditActivity.this,
                        TodoEditActivity.this,
                        c.get(Calendar.HOUR_OF_DAY),
                        c.get(Calendar.MINUTE),
                        true);
                dialog.show();
            }
        });
    }

    private void setupCheckBox() {
        completedCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                UIUtils.setStrikeThough(todoEditView, isChecked);
                todoEditView.setTextColor(isChecked ? Color.GRAY : Color.WHITE);
            }
        });

        View completedWrapper = findViewById(R.id.todo_edit_complete_wrapper);
        completedWrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                completedCb.setChecked(!completedCb.isChecked());
            }
        });
    }

    private void setupSaveButton() {
        findViewById(R.id.todo_detail_done_fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveAndExit();
            }
        });
    }


    private void delete() {
        Intent intent = new Intent();
        intent.putExtra(KEY_TODO_ID, todo.id);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
        // this method will be called after user has chosen date from the DatePickerDialog
        Calendar c = getCalendarFromRemindDate();
        c.set(year, monthOfYear, dayOfMonth);

        remindDate = c.getTime();
        // show on screen
        dateView.setText(DateUtils.dateToStringDate(remindDate));
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        // this method will be called after user has chosen time from the TimePickerDialog
        Calendar c = getCalendarFromRemindDate();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);

        remindDate = c.getTime();
        // show on screen
        timeView.setText(DateUtils.dateToStringTime(remindDate));
    }

    private void saveAndExit() {
        if (todo == null) {
            todo = new Todo(todoEditView.getText().toString(), remindDate);
        } else {
            todo.text = todoEditView.getText().toString();
            todo.remindDate = remindDate;
        }
        todo.done = completedCb.isChecked();

        if (remindDate != null) {
            AlarmUtils.setAlarm(this, todo);
        }

        Intent resultData = new Intent();
        resultData.putExtra(KEY_TODO, todo);
        setResult(Activity.RESULT_OK, resultData);
        finish();
    }

    public Calendar getCalendarFromRemindDate() {
        Calendar c = Calendar.getInstance();
        if (remindDate != null) {
            c.setTime(remindDate);
        }
        return c;
    }
}
