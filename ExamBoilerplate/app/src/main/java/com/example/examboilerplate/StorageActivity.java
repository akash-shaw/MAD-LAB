package com.example.examboilerplate;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

/**
 * STORAGE ACTIVITY: Demonstrates data persistence.
 * 1. SHARED PREFERENCES: Best for key-value pairs (settings, single strings).
 * 2. SQLITE DATABASE: Best for structured data (user lists, multiple rows).
 */
public class StorageActivity extends AppCompatActivity {

    DatabaseHelper myDb; // SQLite helper class instance
    EditText etPrefName, etSqlName, etSqlId;
    TextView tvPrefResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);

        // Initialize SQLite helper
        myDb = new DatabaseHelper(this);

        // --- SECTION 1: SHARED PREFERENCES (Key-Value) ---
        etPrefName = findViewById(R.id.etPrefName);
        tvPrefResult = findViewById(R.id.tvPrefResult);
        
        // 1A. SAVE: How to store data in Preferences
        findViewById(R.id.btnSavePref).setOnClickListener(v -> {
            // Get SharedPreferences (Filename, Access Mode)
            SharedPreferences pref = getSharedPreferences("MyPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            
            // Put Key and Value
            editor.putString("user_name", etPrefName.getText().toString());
            
            // Apply (Asynchronous) vs Commit (Synchronous)
            editor.apply(); 
            Toast.makeText(this, "Saved to Preferences", Toast.LENGTH_SHORT).show();
        });

        // 1B. LOAD: How to retrieve data from Preferences
        findViewById(R.id.btnLoadPref).setOnClickListener(v -> {
            SharedPreferences pref = getSharedPreferences("MyPrefs", MODE_PRIVATE);
            // Get Key (Key, Default value if key is not found)
            String name = pref.getString("user_name", "No Name Found");
            tvPrefResult.setText("Loaded: " + name);
        });

        // --- SECTION 2: SQLITE LOGIC (Structured CRUD) ---
        etSqlName = findViewById(R.id.etSqlName);
        etSqlId = findViewById(R.id.etSqlId);

        // 2A. CREATE: Insert data into SQLite
        findViewById(R.id.btnSqlAdd).setOnClickListener(v -> {
            boolean isInserted = myDb.insertData(etSqlName.getText().toString());
            Toast.makeText(this, isInserted ? "User Added" : "Error adding user", Toast.LENGTH_SHORT).show();
        });

        // 2B. READ: View all data in a Dialog
        findViewById(R.id.btnSqlView).setOnClickListener(v -> {
            Cursor res = myDb.getAllData(); // Get Cursor from DatabaseHelper
            if (res.getCount() == 0) {
                showDialog("Error", "No data found");
                return;
            }
            
            // Use StringBuilder to format results for display
            StringBuilder buffer = new StringBuilder();
            while (res.moveToNext()) {
                // Column indexes: 0 is ID, 1 is Name
                buffer.append("ID: ").append(res.getString(0)).append("\n");
                buffer.append("Name: ").append(res.getString(1)).append("\n\n");
            }
            showDialog("Users List", buffer.toString());
        });

        // 2C. UPDATE: Modify row based on ID
        findViewById(R.id.btnSqlUpdate).setOnClickListener(v -> {
            boolean isUpdate = myDb.updateData(etSqlId.getText().toString(), etSqlName.getText().toString());
            Toast.makeText(this, isUpdate ? "Updated" : "Update Failed", Toast.LENGTH_SHORT).show();
        });

        // 2D. DELETE: Remove row based on ID
        findViewById(R.id.btnSqlDelete).setOnClickListener(v -> {
            Integer deletedRows = myDb.deleteData(etSqlId.getText().toString());
            Toast.makeText(this, deletedRows > 0 ? "Deleted" : "Delete Failed", Toast.LENGTH_SHORT).show();
        });
    }

    /**
     * UTILITY: Show a simple alert dialog to display multi-row data.
     */
    private void showDialog(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }
}
