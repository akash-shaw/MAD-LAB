package com.example.foodorderingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class OrderSummaryActivity extends AppCompatActivity {

    TextView tvOrderDetails;
    SeekBar sbRating;
    Button btnRate, btnOrderMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);

        tvOrderDetails = findViewById(R.id.tvOrderDetails);
        sbRating = findViewById(R.id.sbRating);
        btnRate = findViewById(R.id.btnRate);
        btnOrderMore = findViewById(R.id.btnOrderMore);

        // 1. Display Data
        String orderData = getIntent().getStringExtra("ORDER_DATA");
        if (orderData != null) {
            tvOrderDetails.setText(orderData);
        }

        // 2. Rate Button Logic
        btnRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int rating = sbRating.getProgress();
                Toast.makeText(OrderSummaryActivity.this, "Rating submitted: " + rating + "/10", Toast.LENGTH_SHORT).show();
            }
        });

        // 3. Order More Button Logic
        btnOrderMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderSummaryActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }
}