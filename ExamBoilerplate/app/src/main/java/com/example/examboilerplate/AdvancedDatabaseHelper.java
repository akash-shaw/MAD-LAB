package com.example.examboilerplate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * ---------------------------------------------------------
 * ADVANCED SQLITE HELPER CLASS
 * ---------------------------------------------------------
 * WHAT THIS DOES:
 * 1. Defines the database structure for complex data (Text, Int, Boolean, etc).
 * 2. Handles the creation of the database and its tables.
 * 3. Provides clean methods for Insert (Create) and Query (Read) operations.
 *
 * EXAM TIPS:
 * - Always use 'getWritableDatabase()' for Insert, Update, and Delete.
 * - Use 'ContentValues' to safely map Java variables to SQL columns.
 * - Boolean values (true/false) are stored as 1 and 0 in SQLite.
 */
public class AdvancedDatabaseHelper extends SQLiteOpenHelper {

    // 1. DATABASE CONFIGURATION
    private static final String DATABASE_NAME = "AdvancedDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "advanced_users";

    public AdvancedDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // 2. CREATE TABLE LOGIC: Runs when the database is first initialized
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "age INTEGER, " +
                "joined_date TEXT, " +
                "joined_time TEXT, " +
                "is_active INTEGER)"; // 0 for false, 1 for true
        db.execSQL(createTable);
    }

    // 3. UPGRADE LOGIC: Runs if DATABASE_VERSION is increased
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME); // Delete old data
        onCreate(db); // Create fresh table
    }

    /**
     * INSERT METHOD: How to save different data types.
     * Use this when the Save button is clicked in the Activity.
     */
    public boolean insertData(String name, int age, String date, String time, boolean isActive) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        
        // Mapping: contentValues.put("COLUMN_NAME", VALUE)
        contentValues.put("name", name);
        contentValues.put("age", age);
        contentValues.put("joined_date", date);
        contentValues.put("joined_time", time);
        contentValues.put("is_active", isActive ? 1 : 0); // Convert boolean to int
        
        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1; // -1 indicates an error occurred
    }

    /**
     * QUERY METHOD: How to get all stored records.
     * Returns a 'Cursor' which points to the result set.
     */
    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        // SQL query to select all columns from the table
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }
}
