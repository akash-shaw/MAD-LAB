package com.example.taskmanagerapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class FormActivity extends AppCompatActivity {

    EditText etName, etDate;
    Spinner spinnerPriority;
    Button btnSave;
    DBHelper db;
    String taskId = null;
    long selectedTimestamp = 0; // To store result for DB

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        etName = findViewById(R.id.etName);
        etDate = findViewById(R.id.etDate);
        spinnerPriority = findViewById(R.id.spinnerPriority);
        btnSave = findViewById(R.id.btnSave);
        db = new DBHelper(this);

        // Spinner Setup
        String[] priorities = {"High", "Medium", "Low"};
        spinnerPriority.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, priorities));

        // Date & Time Picker Logic
        etDate.setOnClickListener(v -> showDateTimePicker());

        // Check Edit Mode
        if (getIntent().hasExtra("id")) {
            taskId = getIntent().getStringExtra("id");
            etName.setText(getIntent().getStringExtra("name"));
            selectedTimestamp = getIntent().getLongExtra("date", 0);
            etDate.setText(DateFormat.format("dd/MM/yyyy HH:mm", selectedTimestamp));
        }

        // Save Logic
        btnSave.setOnClickListener(v -> {
            String name = etName.getText().toString();
            String priority = spinnerPriority.getSelectedItem().toString();

            if (selectedTimestamp == 0) {
                Toast.makeText(this, "Please select a date", Toast.LENGTH_SHORT).show();
                return;
            }

            if (taskId == null) {
                if (db.insertTask(name, selectedTimestamp, priority))
                    Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
            } else {
                if (db.updateTask(taskId, name, selectedTimestamp, priority))
                    Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();
            }
            finish();
        });
    }

    private void showDateTimePicker() {
        Calendar calendar = Calendar.getInstance();

        // 1. Pick Date
        new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            // 2. Pick Time (after Date is picked)
            new TimePickerDialog(this, (timeView, hourOfDay, minute) -> {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);

                selectedTimestamp = calendar.getTimeInMillis();
                etDate.setText(DateFormat.format("dd/MM/yyyy HH:mm", selectedTimestamp)); // Show formatted text
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();

        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }
}