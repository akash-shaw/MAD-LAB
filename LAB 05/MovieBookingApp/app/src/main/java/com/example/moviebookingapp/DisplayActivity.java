package com.example.moviebookingapp;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class DisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        TextView tvDetails = findViewById(R.id.tvDetails);
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            String movie = extras.getString("MOVIE");
            String theatre = extras.getString("THEATRE");
            String date = extras.getString("DATE");
            String time = extras.getString("TIME");
            String type = extras.getString("TYPE");

            // Simulate available seats
            int seats = new Random().nextInt(50) + 1;

            String result = "Movie: " + movie + "\n" +
                    "Theatre: " + theatre + "\n" +
                    "Date: " + date + "\n" +
                    "Time: " + time + "\n" +
                    "Type: " + type + "\n\n" +
                    "Available Seats: " + seats;

            tvDetails.setText(result);
        }
    }
}