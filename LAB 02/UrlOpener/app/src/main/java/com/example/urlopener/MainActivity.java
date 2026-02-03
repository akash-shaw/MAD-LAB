package com.example.urlopener; // Keep your actual package name here

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Link code to XML elements
        EditText urlInput = findViewById(R.id.etUrl);
        Button openBtn = findViewById(R.id.btnOpen);

        // Set button click action
        openBtn.setOnClickListener(v -> {
            String url = urlInput.getText().toString();

            // Simple check to ensure http protocol exists
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "https://" + url;
            }

            // Create intent to open web browser
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        });
    }
}