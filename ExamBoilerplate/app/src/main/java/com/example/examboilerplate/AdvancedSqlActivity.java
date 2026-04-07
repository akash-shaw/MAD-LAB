package com.example.examboilerplate;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

/**
 * ---------------------------------------------------------
 * ADVANCED SQLITE ACTIVITY
 * ---------------------------------------------------------
 * WHAT THIS DOES:
 * 1. Collects diverse data (Text, Int, Boolean, Date, Time) from the user.
 * 2. Saves the data into an SQLite database.
 * 3. Dynamically populates a TableLayout to display all stored records.
 *
 * KEY COMPONENTS:
 * - Date/Time Pickers: For picking formatted dates/times.
 * - TableLayout: For grid-like display of database records.
 * - HorizontalScrollView: To make sure wide tables can be scrolled.
 */
public class AdvancedSqlActivity extends AppCompatActivity {

    AdvancedDatabaseHelper db; // Database helper instance
    EditText etName, etAge;
    TextView tvDate, tvTime;
    CheckBox cbActive;
    TableLayout tableLayout;
    String selectedDate = "", selectedTime = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_sql);

        db = new AdvancedDatabaseHelper(this); // Init the database
        initViews();    // Step 1: Link XML to Java
        setupPickers(); // Step 2: Configure Date/Time dialogs
        
        // Step 3: Configure Save button
        findViewById(R.id.btnSaveAdvanced).setOnClickListener(v -> saveToDb());
        
        // Step 4: Initial data load into the table
        refreshTable();
    }

    private void initViews() {
        etName = findViewById(R.id.etAdvName);
        etAge = findViewById(R.id.etAdvAge);
        tvDate = findViewById(R.id.tvSelectedDate);
        tvTime = findViewById(R.id.tvSelectedTime);
        cbActive = findViewById(R.id.cbIsActive);
        tableLayout = findViewById(R.id.advancedTableLayout);
    }

    /**
     * DIALOG PICKERS: Logic to show popups for Date and Time.
     * Uses Calendar to get current time as default.
     */
    private void setupPickers() {
        findViewById(R.id.btnPickDate).setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            new DatePickerDialog(this, (view, y, m, d) -> {
                selectedDate = d + "/" + (m + 1) + "/" + y;
                tvDate.setText("Selected Date: " + selectedDate);
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
        });

        findViewById(R.id.btnPickTime).setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            new TimePickerDialog(this, (view, h, min) -> {
                selectedTime = h + ":" + String.format("%02d", min);
                tvTime.setText("Selected Time: " + selectedTime);
            }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true).show();
        });
    }

    /**
     * SAVE LOGIC: Validates input, parses types, and inserts into DB.
     */
    private void saveToDb() {
        String name = etName.getText().toString();
        String ageStr = etAge.getText().toString();

        // Simple Validation
        if (name.isEmpty() || ageStr.isEmpty() || selectedDate.isEmpty() || selectedTime.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int age = Integer.parseInt(ageStr);      // Convert String to Int
        boolean isActive = cbActive.isChecked(); // Get boolean from checkbox

        // Database Insertion
        boolean success = db.insertData(name, age, selectedDate, selectedTime, isActive);
        if (success) {
            Toast.makeText(this, "Record Saved", Toast.LENGTH_SHORT).show();
            refreshTable(); // Re-load the table with the new record
        }
    }

    /**
     * TABLE REFRESH: Reads from DB and dynamically adds rows to TableLayout.
     */
    private void refreshTable() {
        // Clear all previous rows except the header (index 0)
        int childCount = tableLayout.getChildCount();
        if (childCount > 1) {
            tableLayout.removeViews(1, childCount - 1);
        }

        // Query the database
        Cursor res = db.getAllData();
        while (res.moveToNext()) {
            // 1. Create a new TableRow
            TableRow row = new TableRow(this);
            row.setPadding(5, 10, 5, 10);

            // 2. Add cells (TextViews) for each column in the database
            row.addView(createCell(res.getString(0))); // Column: ID
            row.addView(createCell(res.getString(1))); // Column: Name
            row.addView(createCell(res.getString(2))); // Column: Age
            row.addView(createCell(res.getString(3))); // Column: Date
            row.addView(createCell(res.getString(4))); // Column: Time
            row.addView(createCell(res.getInt(5) == 1 ? "Yes" : "No")); // Column: IsActive

            // 3. Add the row to the TableLayout
            tableLayout.addView(row);
        }
    }

    // Helper method to create a TextView styled for a table cell
    private TextView createCell(String text) {
        TextView tv = new TextView(this);
        tv.setText(text);
        tv.setPadding(10, 10, 10, 10);
        return tv;
    }
}
