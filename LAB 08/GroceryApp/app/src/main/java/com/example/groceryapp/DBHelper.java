package com.example.groceryapp; // Ensure this matches your package name

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    // DB Constants
    private static final String DB_NAME = "GroceryDB";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "Cart";
    private static final String COL_ITEM = "item_name";
    private static final String COL_COST = "cost";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create table query
        String createTable = "CREATE TABLE " + TABLE_NAME + " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_ITEM + " TEXT, " + COL_COST + " REAL)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Insert grocery item
    public boolean insertItem(String item, double cost) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_ITEM, item);
        values.put(COL_COST, cost);
        long result = db.insert(TABLE_NAME, null, values);
        return result != -1;
    }

    // Calculate total cost
    public double getTotalCost() {
        SQLiteDatabase db = this.getReadableDatabase();
        double total = 0;
        // Sum query
        Cursor cursor = db.rawQuery("SELECT SUM(" + COL_COST + ") FROM " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            total = cursor.getDouble(0);
        }
        cursor.close();
        return total;
    }

    // Get all items (returns a Cursor to iterate through)
    public Cursor getAllItems() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    // Delete item by ID
    public void deleteItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "id=?", new String[]{String.valueOf(id)});
    }
}