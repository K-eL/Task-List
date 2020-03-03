package com.thorschmidt.curso.tasklist.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.thorschmidt.curso.tasklist.R;
import com.thorschmidt.curso.tasklist.data.TaskDAO;
import com.thorschmidt.curso.tasklist.model.Task;

public class AddTaskActivity extends AppCompatActivity {

    private Task editableTask;
    private TextInputEditText txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        txt = findViewById(R.id.txtInputTask);

        editableTask = (Task)getIntent().getSerializableExtra("selectedTask");
        if (editableTask != null){
            txt.setText(editableTask.getTaskName());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.menu_Save){
                saveTask();
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Saves a task into the database
     */
    private void saveTask() {
        String taskText = "";
        if (txt.getText() != null){
            taskText = txt.getText().toString();
        }
        if (!taskText.isEmpty()){
            TaskDAO td = new TaskDAO(getApplicationContext());
            Task tsk = new Task(taskText);

            // if I'm editing a task
            if (editableTask != null) {
                tsk.setTaskId(editableTask.getTaskId());
                if (td.update(tsk)){
                    Toast.makeText(this, "Updated!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "Error updating!", Toast.LENGTH_SHORT).show();
                }
            }else{ // if it's a new one
                if (td.save(tsk)){
                    Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "Error saving!", Toast.LENGTH_SHORT).show();
                }
            }

        }else {
            Toast.makeText(this, "Nothing changed!", Toast.LENGTH_SHORT).show();
        }
    }
}
