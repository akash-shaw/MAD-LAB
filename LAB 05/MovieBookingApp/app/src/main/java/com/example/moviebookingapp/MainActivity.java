package com.example.moviebookingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Spinner spMovie, spTheatre;
    DatePicker datePicker;
    TimePicker timePicker;
    ToggleButton toggleTicket;
    Button btnBook, btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Init Views
        spMovie = findViewById(R.id.spinnerMovie);
        spTheatre = findViewById(R.id.spinnerTheatre);
        datePicker = findViewById(R.id.datePicker);
        timePicker = findViewById(R.id.timePicker);
        toggleTicket = findViewById(R.id.toggleTicketType);
        btnBook = findViewById(R.id.btnBook);
        btnReset = findViewById(R.id.btnReset);

        // Setup Spinners
        ArrayAdapter<CharSequence> movieAdapter = ArrayAdapter.createFromResource(this, R.array.movies_array, android.R.layout.simple_spinner_item);
        movieAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spMovie.setAdapter(movieAdapter);

        ArrayAdapter<CharSequence> theatreAdapter = ArrayAdapter.createFromResource(this, R.array.theatres_array, android.R.layout.simple_spinner_item);
        theatreAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTheatre.setAdapter(theatreAdapter);

        // Listeners for Premium Logic
        timePicker.setOnTimeChangedListener((view, hour, minute) -> validatePremiumTime());
        toggleTicket.setOnCheckedChangeListener((buttonView, isChecked) -> validatePremiumTime());

        btnBook.setOnClickListener(v -> bookTicket());
        btnReset.setOnClickListener(v -> resetFields());
    }

    // Check if Premium is selected and Time is before 12 PM (12:00)
    private void validatePremiumTime() {
        boolean isPremium = toggleTicket.isChecked();
        int hour = timePicker.getHour(); // 24-hour format

        if (isPremium && hour < 12) {
            btnBook.setEnabled(false);
            Toast.makeText(this, "Premium tickets only available after 12:00 PM", Toast.LENGTH_SHORT).show();
        } else {
            btnBook.setEnabled(true);
        }
    }

    private void bookTicket() {
        if (spMovie.getSelectedItemPosition() == 0 || spTheatre.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please select Movie and Theatre", Toast.LENGTH_SHORT).show();
            return;
        }

        String date = datePicker.getDayOfMonth() + "/" + (datePicker.getMonth() + 1) + "/" + datePicker.getYear();
        String time = String.format("%02d:%02d", timePicker.getHour(), timePicker.getMinute());
        String ticketType = toggleTicket.isChecked() ? "Premium" : "Standard";

        Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
        intent.putExtra("MOVIE", spMovie.getSelectedItem().toString());
        intent.putExtra("THEATRE", spTheatre.getSelectedItem().toString());
        intent.putExtra("DATE", date);
        intent.putExtra("TIME", time);
        intent.putExtra("TYPE", ticketType);
        startActivity(intent);
    }

    private void resetFields() {
        spMovie.setSelection(0);
        spTheatre.setSelection(0);
        toggleTicket.setChecked(false);

        // Reset Date to current
        Calendar c = Calendar.getInstance();
        datePicker.updateDate(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));

        // Reset Time to current
        timePicker.setHour(c.get(Calendar.HOUR_OF_DAY));
        timePicker.setMinute(c.get(Calendar.MINUTE));

        btnBook.setEnabled(true);
    }
}