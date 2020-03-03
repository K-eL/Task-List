package com.thorschmidt.curso.tasklist.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.thorschmidt.curso.tasklist.model.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskDAO implements ITaskDAO {

    private SQLiteDatabase write;
    private SQLiteDatabase read;

    public TaskDAO(Context context) {
        DB_Helper dbh = new DB_Helper(context);
        write = dbh.getWritableDatabase();
        read = dbh.getReadableDatabase();
    }

    @Override
    public boolean save(Task task) {

        ContentValues cv = new ContentValues();
        cv.put("task_name",task.getTaskName());

        try{
            write.insert(DB_Helper.TB_TASKS,null,cv);
            Log.i("INFO DAO", "save: Task saved with success!");
        }catch(Exception e){
            Log.i("INFO DAO", "save error: " + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean update(Task task) {
        ContentValues cv = new ContentValues();
        cv.put("task_name",task.getTaskName());

        try{
            String[] args = {String.valueOf(task.getTaskId())};
            write.update(DB_Helper.TB_TASKS,cv,"id_task=?",args);
            Log.i("INFO DAO", "update: Task updated with success!");
        }catch(Exception e){
            Log.i("INFO DAO", "update error: " + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean delete(Task task) {
        try{
            String[] args = {String.valueOf(task.getTaskId())};
            write.delete(DB_Helper.TB_TASKS,"id_task=?",args);
            Log.i("INFO DAO", "delete: Task deleted with success!");
        }catch(Exception e){
            Log.i("INFO DAO", "delete error: " + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public List<Task> list() {
        List<Task> list = new ArrayList<>();

        String sqlReadTbTasks = "SELECT * FROM " + DB_Helper.TB_TASKS +
                " ORDER BY id_task ASC";
        Cursor c = read.rawQuery(sqlReadTbTasks,null);

        while (c.moveToNext()){
            Task tsk = new Task(
                    c.getLong(c.getColumnIndex("id_task")),
                    c.getString(c.getColumnIndex("task_name"))
                    );
            list.add(tsk);
        }
        c.close();
        return list;
    }
}
