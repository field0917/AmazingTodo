Fpackage com.jiuzhang.yeyuan.amazingtodo;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.jiuzhang.yeyuan.amazingtodo.models.Todo;
import com.jiuzhang.yeyuan.amazingtodo.utils.UIUtils;

import java.util.List;

public class TodoListAdapter extends RecyclerView.Adapter<TodoListViewHolder> {

    private MainActivity activity;
    private List<Todo> data;

    public TodoListAdapter(MainActivity activity, List<Todo> data) {
        this.activity = activity;
        this.data = data;
    }

    @Override
    public TodoListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.todo_item, parent, false);
        return new TodoListViewHolder(activity, view, data);
    }

    @Override
    public void onBindViewHolder(TodoListViewHolder holder, final int position) {
        Todo todo = data.get(position);
        holder.todoText.setText(todo.text);
        holder.doneTodo.setChecked(todo.done);
        UIUtils.setStrikeThough(holder.todoText, todo.done);
        //holder.doneTodo.setOnCheckedChangeListener(null);
        //holder.doneTodo.setSelected(todo.done);
        holder.doneTodo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                activity.updateTodo(position, isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

//    public void deleteTodo(int position) {
//        data.remove(position);
//        notifyItemRemoved(position);
//    }
}
