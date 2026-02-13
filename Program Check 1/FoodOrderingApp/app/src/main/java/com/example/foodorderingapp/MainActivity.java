package com.example.foodorderingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    CheckBox cbPizza, cbBurger, cbCoffee;
    EditText etPizzaQty, etBurgerQty, etCoffeeQty;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Initialize Views
        cbPizza = findViewById(R.id.cbPizza);
        etPizzaQty = findViewById(R.id.etPizzaQty);

        cbBurger = findViewById(R.id.cbBurger);
        etBurgerQty = findViewById(R.id.etBurgerQty);

        cbCoffee = findViewById(R.id.cbCoffee);
        etCoffeeQty = findViewById(R.id.etCoffeeQty);

        btnSubmit = findViewById(R.id.btnSubmit);

        // 2. Set listeners to show/hide quantity boxes
        setupCheckbox(cbPizza, etPizzaQty);
        setupCheckbox(cbBurger, etBurgerQty);
        setupCheckbox(cbCoffee, etCoffeeQty);

        // 3. Submit Button Logic
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder order = new StringBuilder();
                int total = 0;

                // Calculate costs using helper method
                total += calculateItem(cbPizza, etPizzaQty, "Pizza", 10, order);
                total += calculateItem(cbBurger, etBurgerQty, "Burger", 5, order);
                total += calculateItem(cbCoffee, etCoffeeQty, "Coffee", 3, order);

                // Prevent empty orders
                if (total == 0) {
                    Toast.makeText(MainActivity.this, "Please select items and quantity", Toast.LENGTH_SHORT).show();
                    return;
                }

                order.append("\nTotal Cost: $").append(total);

                // Disable all inputs
                disableInputs();

                // Go to next activity
                Intent intent = new Intent(MainActivity.this, OrderSummaryActivity.class);
                intent.putExtra("ORDER_DATA", order.toString());
                startActivity(intent);
            }
        });
    }

    // Helper: Show/Hide quantity box based on checkbox
    private void setupCheckbox(CheckBox cb, final EditText et) {
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    et.setVisibility(View.VISIBLE);
                    et.setText("1"); // Default quantity
                } else {
                    et.setVisibility(View.INVISIBLE);
                    et.setText("");
                }
            }
        });
    }

    // Helper: Calculate price for a single item
    private int calculateItem(CheckBox cb, EditText et, String name, int price, StringBuilder sb) {
        if (cb.isChecked()) {
            String qtyStr = et.getText().toString();
            // Default to 0 if empty
            int qty = qtyStr.isEmpty() ? 0 : Integer.parseInt(qtyStr);

            if (qty > 0) {
                int itemTotal = price * qty;
                sb.append(name).append(" (x").append(qty).append("): $").append(itemTotal).append("\n");
                return itemTotal;
            }
        }
        return 0;
    }

    // Helper: Lock the screen after submit
    private void disableInputs() {
        cbPizza.setEnabled(false); etPizzaQty.setEnabled(false);
        cbBurger.setEnabled(false); etBurgerQty.setEnabled(false);
        cbCoffee.setEnabled(false); etCoffeeQty.setEnabled(false);
        btnSubmit.setEnabled(false);
    }
}