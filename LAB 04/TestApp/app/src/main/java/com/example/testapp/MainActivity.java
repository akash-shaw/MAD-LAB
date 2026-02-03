package com.example.testapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find buttons by ID
        Button simpleBtn = findViewById(R.id.myButton);
        ToggleButton toggleBtn = findViewById(R.id.myToggleButton);

        // Set click listener for standard button
        simpleBtn.setOnClickListener(v -> showCustomToast(R.drawable.star));

        // Set click listener for toggle button
        toggleBtn.setOnClickListener(v -> showCustomToast(R.drawable.heart));
    }

    // Helper method to show custom toast
    private void showCustomToast(int imageResId) {
        // 1. Inflate the custom layout
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast_layout, findViewById(R.id.toast_root));

        // 2. Set the specific image
        ImageView image = layout.findViewById(R.id.toastImage);
        image.setImageResource(imageResId);

        // 3. Create and show the Toast
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
}