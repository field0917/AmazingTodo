package com.jiuzhang.yeyuan.amazingtodo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.jiuzhang.yeyuan.amazingtodo.models.Todo;
import com.jiuzhang.yeyuan.amazingtodo.utils.UIUtils;

import java.util.List;


public class TodoListViewHolder extends RecyclerView.ViewHolder {
    public MainActivity activity;
    public TextView todoText;
    public CheckBox doneTodo;

    public TodoListViewHolder(final MainActivity activity, View itemView, final List<Todo> todoList) {
        super(itemView);
        this.activity = activity;
        this.todoText = itemView.findViewById(R.id.todo_item_text);
        this.doneTodo = itemView.findViewById(R.id.todo_item_check);

//        doneTodo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
//                activity.updateTodo(getAdapterPosition(), isChecked);
//            }
//        });

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Todo todo = todoList.get(getAdapterPosition());
                Intent intent = new Intent(activity, TodoEditActivity.class);
                intent.putExtra(TodoEditActivity.KEY_TODO, todo);
                activity.startActivityForResult(intent, MainActivity.REQ_CODE_TODO_EDIT);

            }
        });

        doneTodo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                activity.updateTodo(position, isChecked);
            }
        });


    }
}
