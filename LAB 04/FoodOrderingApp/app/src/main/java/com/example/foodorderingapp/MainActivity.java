package com.example.foodorderingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    CheckBox cbPizza, cbBurger, cbCoffee;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Views
        cbPizza = findViewById(R.id.cbPizza);
        cbBurger = findViewById(R.id.cbBurger);
        cbCoffee = findViewById(R.id.cbCoffee);
        btnSubmit = findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder order = new StringBuilder();
                int total = 0;

                // Check items and calculate cost
                if (cbPizza.isChecked()) {
                    order.append("Pizza: $10\n");
                    total += 10;
                }
                if (cbBurger.isChecked()) {
                    order.append("Burger: $5\n");
                    total += 5;
                }
                if (cbCoffee.isChecked()) {
                    order.append("Coffee: $3\n");
                    total += 3;
                }

                order.append("\nTotal Cost: $").append(total);

                // Disable checkboxes so order cannot be changed
                cbPizza.setEnabled(false);
                cbBurger.setEnabled(false);
                cbCoffee.setEnabled(false);
                btnSubmit.setEnabled(false); // Optional: disable button too

                // Navigate to next activity
                Intent intent = new Intent(MainActivity.this, OrderSummaryActivity.class);
                intent.putExtra("ORDER_DATA", order.toString());
                startActivity(intent);
            }
        });
    }
}