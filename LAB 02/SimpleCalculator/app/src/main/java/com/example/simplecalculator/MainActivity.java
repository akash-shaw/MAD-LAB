package com.example.simplecalculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText etNum1, etNum2;
    Button btnAdd, btnSub, btnMul, btnDiv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Link Java variables to XML components
        etNum1 = findViewById(R.id.etNum1);
        etNum2 = findViewById(R.id.etNum2);
        btnAdd = findViewById(R.id.btnAdd);
        btnSub = findViewById(R.id.btnSub);
        btnMul = findViewById(R.id.btnMul);
        btnDiv = findViewById(R.id.btnDiv);

        // Set click listeners for each button
        btnAdd.setOnClickListener(v -> calculate("+"));
        btnSub.setOnClickListener(v -> calculate("-"));
        btnMul.setOnClickListener(v -> calculate("*"));
        btnDiv.setOnClickListener(v -> calculate("/"));
    }

    // Helper function to calculate and switch screens
    void calculate(String operator) {
        String s1 = etNum1.getText().toString();
        String s2 = etNum2.getText().toString();

        // Check if inputs are empty
        if (s1.isEmpty() || s2.isEmpty()) {
            Toast.makeText(this, "Please enter numbers", Toast.LENGTH_SHORT).show();
            return;
        }

        double n1 = Double.parseDouble(s1);
        double n2 = Double.parseDouble(s2);
        double result = 0;

        // Perform Math
        if (operator.equals("+")) result = n1 + n2;
        else if (operator.equals("-")) result = n1 - n2;
        else if (operator.equals("*")) result = n1 * n2;
        else if (operator.equals("/")) result = n1 / n2;

        // Format: Num1 operator num2 = result
        String finalOutput = n1 + " " + operator + " " + n2 + " = " + result;

        // Navigate to ResultActivity
        Intent intent = new Intent(MainActivity.this, ResultActivity.class);
        intent.putExtra("RESULT_DATA", finalOutput); // Send data
        startActivity(intent);
    }
}