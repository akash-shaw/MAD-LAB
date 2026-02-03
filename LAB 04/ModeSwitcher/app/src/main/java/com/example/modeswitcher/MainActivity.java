package com.example.modeswitcher;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    ToggleButton toggleButton;
    ImageView imageView;
    Button btnChangeMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Initialize Views
        toggleButton = findViewById(R.id.toggleButton);
        imageView = findViewById(R.id.imageView);
        btnChangeMode = findViewById(R.id.btnChangeMode);

        // 2. Set listener for ToggleButton state changes
        toggleButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // WiFi State
                imageView.setImageResource(R.drawable.ic_wifi);
                Toast.makeText(MainActivity.this, "Current Mode: Wi-Fi", Toast.LENGTH_SHORT).show();
            } else {
                // Mobile Data State
                imageView.setImageResource(R.drawable.ic_data);
                Toast.makeText(MainActivity.this, "Current Mode: Mobile Data", Toast.LENGTH_SHORT).show();
            }
        });

        // 3. Set listener for "Change Mode" button
        btnChangeMode.setOnClickListener(v -> {
            // Simply flip the current state; the listener above will handle the rest
            toggleButton.toggle();
        });
    }
}