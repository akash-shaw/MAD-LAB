package com.example.taskmanagerapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "TaskDB.db", null, 2); // Version updated to 2
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Changed date to INTEGER to store timestamp
        db.execSQL("CREATE TABLE tasks (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, date INTEGER, priority TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tasks");
        onCreate(db);
    }

    // Insert task with timestamp
    public boolean insertTask(String name, long date, String priority) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("date", date);
        cv.put("priority", priority);
        return db.insert("tasks", null, cv) != -1;
    }

    // Update task with timestamp
    public boolean updateTask(String id, String name, long date, String priority) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("date", date);
        cv.put("priority", priority);
        return db.update("tasks", cv, "id=?", new String[]{id}) > 0;
    }

    public boolean deleteTask(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("tasks", "id=?", new String[]{id}) > 0;
    }

    public Cursor getAllTasks() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM tasks ORDER BY date ASC", null); // Sort by date
    }
}