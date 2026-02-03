package com.example.layoutdemo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Link buttons and set click listeners to open specific activities
        findViewById(R.id.btnList).setOnClickListener(v -> startActivity(new Intent(this, ListViewActivity.class)));
        findViewById(R.id.btnGrid).setOnClickListener(v -> startActivity(new Intent(this, GridViewActivity.class)));
        findViewById(R.id.btnTab).setOnClickListener(v -> startActivity(new Intent(this, TabLayoutActivity.class)));
        findViewById(R.id.btnTable).setOnClickListener(v -> startActivity(new Intent(this, TableLayoutActivity.class)));
    }
}