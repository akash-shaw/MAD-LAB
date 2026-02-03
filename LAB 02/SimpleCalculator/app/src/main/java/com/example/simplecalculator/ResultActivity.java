package com.example.simplecalculator;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView tvResult = findViewById(R.id.tvResult);

        // Get the data passed from the previous activity
        String data = getIntent().getStringExtra("RESULT_DATA");

        // Display the data
        if(data != null) {
            tvResult.setText(data);
        }
    }
}