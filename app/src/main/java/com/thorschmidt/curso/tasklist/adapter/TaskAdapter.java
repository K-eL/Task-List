package com.thorschmidt.curso.tasklist.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thorschmidt.curso.tasklist.R;
import com.thorschmidt.curso.tasklist.model.Task;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MyViewHolder> {

    private List<Task> taskList;

    public TaskAdapter(List<Task> list) {
        this.taskList = list;
    }

    // create layout from xml
    // res/layout/ new > 'layout resource file'
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // convert the layout xml into a View object
        // parent is the list
        View itemList = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_tasklist,parent,false);

        return new MyViewHolder(itemList);
    }

    // show each element created in onCreate
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        // pego o item da lista de acordo com a posição do holder na view;
        Task task = taskList.get(position);
        // atribuo os parametros do item na lista no holder chamado
        holder.task.setText(task.getTaskName());
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    // serve para configurar a exibição das informações dentro do recyclerview
    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView task;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            task = itemView.findViewById(R.id.lblTask);
        }
    }
}
