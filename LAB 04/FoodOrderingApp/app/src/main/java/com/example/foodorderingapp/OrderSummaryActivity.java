package com.example.foodorderingapp;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class OrderSummaryActivity extends AppCompatActivity {

    TextView tvOrderDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);

        tvOrderDetails = findViewById(R.id.tvOrderDetails);

        // Get data passed from MainActivity
        String orderData = getIntent().getStringExtra("ORDER_DATA");

        // Display data
        if (orderData != null) {
            tvOrderDetails.setText(orderData);
        }
    }
}