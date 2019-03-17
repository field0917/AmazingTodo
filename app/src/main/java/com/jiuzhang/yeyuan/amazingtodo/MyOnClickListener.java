package com.jiuzhang.yeyuan.amazingtodo;


import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jiuzhang.yeyuan.amazingtodo.models.Todo;

import java.util.List;

public class MyOnClickListener implements View.OnClickListener {

    private MainActivity activity;

    private RecyclerView mRecyclerView;

    private List<Todo> data;

    public MyOnClickListener(List<Todo> data) {
        this.activity = new MainActivity();
        this.data = data;
        mRecyclerView = activity.findViewById(R.id.main_recycler_view);
    }

    @Override
    public void onClick(View view) {
        int position = mRecyclerView.getChildLayoutPosition(view);
        Intent intent = new Intent(activity, TodoEditActivity.class);
        intent.putExtra(TodoEditActivity.KEY_TODO, data.get(position));
    }
}
