package com.thorschmidt.curso.tasklist.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DB_Helper extends SQLiteOpenHelper {

    public static String DB_NAME = "DB_TASKS";
    public static String TB_TASKS = "TB_TASKS";
    public static int version = 1;

    public DB_Helper(@Nullable Context context) {
        super(context, DB_NAME, null, version);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTbTasks(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * Creating the table to include all user's tasks
     * @param db SQLite Database
     */
    private void createTbTasks(SQLiteDatabase db) {
        String sqlCreateTbTasks = "CREATE TABLE IF NOT EXISTS " + TB_TASKS +
                " (id_task INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "task_name TEXT NOT NULL);";

        try {
            db.execSQL(sqlCreateTbTasks);
            Log.i("Info DB", "onCreate: Successfully created!");
        }catch (Exception e){
            Log.i("Info DB", "onCreate: " + e.getMessage());
        }
    }
}
