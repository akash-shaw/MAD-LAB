package com.example.travelbookingapp; // Check your package name!

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Spinner spinSource, spinDest;
    Button btnDate, btnSubmit, btnReset;
    ToggleButton toggleTrip;
    String selectedDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Init views
        spinSource = findViewById(R.id.spinnerSource);
        spinDest = findViewById(R.id.spinnerDest);
        btnDate = findViewById(R.id.btnDate);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnReset = findViewById(R.id.btnReset);
        toggleTrip = findViewById(R.id.toggleTripType);

        // Setup Spinners with data from strings.xml
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.cities, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinSource.setAdapter(adapter);
        spinDest.setAdapter(adapter);

        // DatePicker Logic
        btnDate.setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    (view, year1, month1, dayOfMonth) -> {
                        selectedDate = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
                        btnDate.setText(selectedDate);
                    }, year, month, day);
            datePickerDialog.show();
        });

        // Submit Button Logic
        btnSubmit.setOnClickListener(v -> {
            if (selectedDate.isEmpty()) {
                Toast.makeText(this, "Please select a date", Toast.LENGTH_SHORT).show();
                return;
            }

            String source = spinSource.getSelectedItem().toString();
            String dest = spinDest.getSelectedItem().toString();
            String type = toggleTrip.isChecked() ? "Round Trip" : "One Way";

            // Pass data to next activity
            Intent intent = new Intent(MainActivity.this, TicketDetailsActivity.class);
            intent.putExtra("SOURCE", source);
            intent.putExtra("DEST", dest);
            intent.putExtra("DATE", selectedDate);
            intent.putExtra("TYPE", type);
            startActivity(intent);
        });

        // Reset Button Logic
        btnReset.setOnClickListener(v -> {
            spinSource.setSelection(0);
            spinDest.setSelection(0);
            toggleTrip.setChecked(false);

            // Reset date to current system date
            final Calendar c = Calendar.getInstance();
            selectedDate = c.get(Calendar.DAY_OF_MONTH) + "/" +
                    (c.get(Calendar.MONTH) + 1) + "/" +
                    c.get(Calendar.YEAR);
            btnDate.setText("Select Date"); // Or set to current date string
            selectedDate = ""; // Clear selection tracking
        });
    }
}