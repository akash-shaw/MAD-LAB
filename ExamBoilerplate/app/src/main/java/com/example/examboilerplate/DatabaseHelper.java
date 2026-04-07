package com.example.examboilerplate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * DATABASE HELPER: Handles all SQLite operations (CRUD).
 * Extends SQLiteOpenHelper to manage database creation and version management.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    // 1. CONSTANTS: Database and Table Information
    private static final String DATABASE_NAME = "UserDB"; // Name of the .db file
    private static final int DATABASE_VERSION = 1;        // Version (increment this to trigger onUpgrade)
    
    private static final String TABLE_NAME = "users";     // Table name
    private static final String COLUMN_ID = "id";         // Column 1: Primary Key (Auto-increment)
    private static final String COLUMN_NAME = "name";     // Column 2: User Name

    // 2. CONSTRUCTOR: Initializes the database
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // 3. ON CREATE: Runs once when the database is first created
    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL Statement to create the table
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT)";
        db.execSQL(createTable); // Executes the SQL
    }

    // 4. ON UPGRADE: Runs when the DATABASE_VERSION is increased
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME); // Delete old table
        onCreate(db); // Create new one
    }

    // --- CRUD OPERATIONS ---

    /**
     * CREATE: Inserts a new user name into the database.
     * @return true if insertion was successful.
     */
    public boolean insertData(String name) {
        SQLiteDatabase db = this.getWritableDatabase(); // Get write access
        ContentValues contentValues = new ContentValues(); // Key-Value pair storage
        contentValues.put(COLUMN_NAME, name); // (Column_Name, Value)
        
        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1; // -1 means error
    }

    /**
     * READ: Retrieves all rows from the table.
     * @return A Cursor containing the data.
     */
    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        // RawQuery executes the SELECT statement
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    /**
     * UPDATE: Modifies an existing row based on ID.
     * @return true if update statement was executed.
     */
    public boolean updateData(String id, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, name);
        
        // table, contentValues, WHERE clause, WHERE arguments
        db.update(TABLE_NAME, contentValues, "id = ?", new String[]{id});
        return true;
    }

    /**
     * DELETE: Removes a row based on ID.
     * @return The number of rows deleted.
     */
    public Integer deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        // table, WHERE clause, WHERE arguments
        return db.delete(TABLE_NAME, "id = ?", new String[]{id});
    }
}
