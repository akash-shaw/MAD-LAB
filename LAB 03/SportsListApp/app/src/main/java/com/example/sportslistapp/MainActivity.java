package com.example.sportslistapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // 1. Data source
    String[] sports = {"Football", "Basketball", "Tennis", "Cricket", "Badminton", "Rugby", "Golf"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 2. Initialize ListView
        ListView listView = findViewById(R.id.sportsListView);

        // 3. Create adapter using default Android layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                sports
        );

        // 4. Set adapter to list
        listView.setAdapter(adapter);

        // 5. Handle clicks
        listView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedSport = sports[position];
            Toast.makeText(getApplicationContext(), "Selected: " + selectedSport, Toast.LENGTH_SHORT).show();
        });
    }
}