package com.jiuzhang.yeyuan.amazingtodo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.jiuzhang.yeyuan.amazingtodo.R;
import com.jiuzhang.yeyuan.amazingtodo.models.Todo;
import com.jiuzhang.yeyuan.amazingtodo.utils.DateUtils;
import com.jiuzhang.yeyuan.amazingtodo.utils.ModelUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int REQ_CODE_TODO_EDIT = 100;
    private static final String TODOS = "todos";

    private List<Todo> todoList;
    private TodoListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TodoEditActivity.class);
                startActivityForResult(intent, REQ_CODE_TODO_EDIT);
            }
        });

        loadData();
        adapter = new TodoListAdapter(this, todoList);
        setupTodoUI();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQ_CODE_TODO_EDIT:
                    String todoId = data.getStringExtra(TodoEditActivity.KEY_TODO_ID);
                    if (todoId != null) {
                        deleteTodo(todoId);
                    } else {
                        Todo todo = data.getParcelableExtra(TodoEditActivity.KEY_TODO);
                        updateTodoList(todo);
                    }
            }
        }
    }

    private void updateTodoList(Todo todo) {
        int position = -1;
        for (int i = 0; i < todoList.size(); i++) {
            String todoId = todoList.get(i).id;
            if (todoId.equals(todo.id)) {
                position = i;
                todoList.set(i, todo);
                break;
            }
        }
        if (position < 0) {
            todoList.add(todo);
            adapter.notifyItemInserted(todoList.size());///////////////
        } else {
            adapter.notifyItemChanged(position);
        }
        ModelUtils.save(this, TODOS, todoList);
    }

    public void updateTodo(int position, boolean done) {
        todoList.get(position).done = done;
        adapter.notifyDataSetChanged();
        ModelUtils.save(this, TODOS, todoList);
    }

    private void deleteTodo(String id) {
        //int position = 0;
        for (int i = 0; i < todoList.size(); i++) {
            Todo todo = todoList.get(i);
            if (TextUtils.equals(todo.id, id)) {
                //position = i;
                todoList.remove(i);
                adapter.notifyItemRemoved(i);
                adapter.notifyItemRangeChanged(i, todoList.size());
                //adapter.deleteTodo(position);
                break;
            }
        }

        ModelUtils.save(this, TODOS, todoList);
    }

    private void setupTodoUI() {
        RecyclerView recyclerView = findViewById(R.id.main_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(decoration);
    }

    private void loadData() {
        todoList = ModelUtils.read(this, TODOS, new TypeToken<List<Todo>>(){});
        if (todoList == null) {
            todoList = new ArrayList<>();
        }
    }
}
