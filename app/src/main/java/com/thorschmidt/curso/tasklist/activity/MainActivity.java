package com.thorschmidt.curso.tasklist.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.thorschmidt.curso.tasklist.R;
import com.thorschmidt.curso.tasklist.adapter.TaskAdapter;
import com.thorschmidt.curso.tasklist.data.TaskDAO;
import com.thorschmidt.curso.tasklist.databinding.ActivityMainBinding;
import com.thorschmidt.curso.tasklist.listener.RecyclerItemClickListener;
import com.thorschmidt.curso.tasklist.model.Task;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private RecyclerView recView;
    private List<Task> taskList = new ArrayList<>();
    private TaskAdapter adapter;
    private Task tsk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loadTasks();// get tasks
        configAdapter(); // set adapter
        configRecycler(); // set recyclerview
        setListeners(); // set recyclerview item listener

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddTaskActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        loadTasks();// get tasks
        super.onStart();
    }

    /**
     * Loads all tasks from the database
     */
    private void loadTasks() {
        TaskDAO td = new TaskDAO(getApplicationContext());
        this.taskList = td.list();
        configAdapter();
        configRecycler();
    }

    private void configAdapter() {
        adapter = new TaskAdapter(taskList);
    }

    private void setListeners() {
        recView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        recView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                //get the selected task
                                tsk = taskList.get(position);
                                //send to the edit screen
                                Intent intent = new Intent(getApplicationContext(),AddTaskActivity.class);
                                // add task to intent
                                intent.putExtra("selectedTask", tsk);
                                startActivity(intent);

                                Log.i("click", "onItemClick: ");
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                //get the selected task

                                tsk = taskList.get(position);
                                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                                alert.setTitle("Confirm delete");
                                alert.setMessage("Do you want to delete the task: "+ tsk.getTaskName() + "?");
                                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        TaskDAO taskdao = new TaskDAO(getApplicationContext());
                                        if (taskdao.delete(tsk)){
                                            loadTasks();
                                            Toast.makeText(MainActivity.this, "Deleted!", Toast.LENGTH_SHORT).show();
                                        }else {
                                            Toast.makeText(MainActivity.this, "Error deleting!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                                alert.setNegativeButton("Cancel", null);
                                alert.create();
                                try{
                                    alert.show();
                                }
                                catch(Exception e){
                                    Log.i("click", "onLongItemClick: "+ e.getMessage());
                                }
                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Log.i("click", "onItemClick: ");
                            }
                        }
                ));
    }

    private void configRecycler() {
        recView = findViewById(R.id.recyclerViewTasks);
        recView.setLayoutManager(new LinearLayoutManager(this));
        recView.setHasFixedSize(true);
        recView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
        recView.setAdapter(adapter);
    }

}
