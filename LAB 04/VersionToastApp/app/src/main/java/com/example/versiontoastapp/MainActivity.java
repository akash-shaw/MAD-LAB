package com.example.versiontoastapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find buttons
        Button btnPie = findViewById(R.id.btn_pie);
        Button btnOreo = findViewById(R.id.btn_oreo);
        Button btnNougat = findViewById(R.id.btn_nougat);

        // Set listeners
        btnPie.setOnClickListener(v -> showCustomToast("Android Pie", R.drawable.img_pie));
        btnOreo.setOnClickListener(v -> showCustomToast("Android Oreo", R.drawable.img_oreo));
        btnNougat.setOnClickListener(v -> showCustomToast("Android Nougat", R.drawable.img_nougat));
    }

    private void showCustomToast(String message, int imageResId) {
        // Inflate custom layout
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast, findViewById(R.id.toast_root));

        // Set image and text
        ImageView image = layout.findViewById(R.id.toast_image);
        image.setImageResource(imageResId);

        TextView text = layout.findViewById(R.id.toast_text);
        text.setText(message);

        // Create and show toast
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
}