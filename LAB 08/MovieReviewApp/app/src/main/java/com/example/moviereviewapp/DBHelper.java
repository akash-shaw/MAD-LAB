package com.example.moviereviewapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    // DB and Table info
    private static final String DB_NAME = "MovieDB";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "reviews";
    private static final String COL_NAME = "name";
    private static final String COL_YEAR = "year";
    private static final String COL_RATING = "rating";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create table query
        String query = "CREATE TABLE " + TABLE_NAME + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME + " TEXT, " +
                COL_YEAR + " TEXT, " +
                COL_RATING + " TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Insert new review
    public boolean insertReview(String name, String year, String rating) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAME, name);
        values.put(COL_YEAR, year);
        values.put(COL_RATING, rating);
        long result = db.insert(TABLE_NAME, null, values);
        return result != -1;
    }

    // Get all movie names for ListView
    public Cursor getAllMovies() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    // Get specific movie details
    public Cursor getReviewDetails(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL_NAME + " = ?", new String[]{name});
    }
}