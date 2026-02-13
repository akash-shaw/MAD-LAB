package com.example.travelbookingapp; // Check your package name!

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class TicketDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_details);

        TextView txtDetails = findViewById(R.id.txtDetails);

        // Get data passed from MainActivity
        String source = getIntent().getStringExtra("SOURCE");
        String dest = getIntent().getStringExtra("DEST");
        String date = getIntent().getStringExtra("DATE");
        String type = getIntent().getStringExtra("TYPE");

        // Format and display
        String summary = "Source: " + source + "\n\n" +
                "Destination: " + dest + "\n\n" +
                "Date of Travel: " + date + "\n\n" +
                "Trip Type: " + type;

        txtDetails.setText(summary);
    }
}