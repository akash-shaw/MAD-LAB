package com.example.examboilerplate;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

/**
 * ---------------------------------------------------------
 * INTERACTIVE COMPONENTS ACTIVITY
 * ---------------------------------------------------------
 * WHAT THIS DOES:
 * 1. Demonstrates standard UI widgets (Button, EditText, Checkbox, etc).
 * 2. Shows how to handle clicks and state changes.
 * 3. Shows utility functions like Toasts, Dialogs, and Activity finishing.
 *
 * EXAM TIPS:
 * - Use 'findViewById' to link XML views to Java objects.
 * - 'Toast' is used for quick feedback, 'AlertDialog' for confirmation.
 * - View.GONE vs View.INVISIBLE: GONE removes the element and its space.
 */
public class InteractiveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interactive);

        // --- 1. BUTTONS (Click, Enable, Visibility) ---
        Button btnTest = findViewById(R.id.btnTest);
        Button btnToggleEnable = findViewById(R.id.btnToggleEnable);
        Button btnToggleVisibility = findViewById(R.id.btnToggleVisibility);

        // Simple Click Handler
        btnTest.setOnClickListener(v -> Toast.makeText(this, "Button Clicked!", Toast.LENGTH_SHORT).show());
        
        // Toggle Enabled State (Grey out button)
        btnToggleEnable.setOnClickListener(v -> btnTest.setEnabled(!btnTest.isEnabled()));

        // Toggle Visibility (Show/Hide)
        btnToggleVisibility.setOnClickListener(v -> {
            if (btnTest.getVisibility() == View.VISIBLE) {
                btnTest.setVisibility(View.GONE); // Removes space
                btnToggleVisibility.setText("Show Button Above");
            } else {
                btnTest.setVisibility(View.VISIBLE);
                btnToggleVisibility.setText("Hide Button Above");
            }
        });

        // --- 2. EDITTEXT (Get/Set Text) ---
        EditText etInput = findViewById(R.id.etInput);
        Button btnTextActions = findViewById(R.id.btnTextActions);
        btnTextActions.setOnClickListener(v -> {
            String val = etInput.getText().toString(); // Read from UI
            etInput.setText("Hello " + val);          // Write to UI
        });

        // --- 3. SEEKBAR (Progress Listener) ---
        SeekBar seekBar = findViewById(R.id.seekBar);
        TextView tvSeekVal = findViewById(R.id.tvSeekVal);
        seekBar.setMax(200);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar sb, int p, boolean b) {
                tvSeekVal.setText("Value: " + p); // Updates as user slides
            }
            @Override public void onStartTrackingTouch(SeekBar sb) {}
            @Override public void onStopTrackingTouch(SeekBar sb) {}
        });

        // --- 4. CHECKBOX & SWITCH ---
        CheckBox cb = findViewById(R.id.checkBox);
        Switch sw = findViewById(R.id.switchTest);
        cb.setOnCheckedChangeListener((v, isChecked) -> {
            Toast.makeText(this, "Checked: " + isChecked, Toast.LENGTH_SHORT).show();
        });

        // --- 5. RADIO GROUP (Single Selection) ---
        RadioGroup rg = findViewById(R.id.radioGroup);
        rg.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId != -1) {
                RadioButton rb = findViewById(checkedId);
                Toast.makeText(this, "Selected: " + rb.getText(), Toast.LENGTH_SHORT).show();
            }
        });

        // --- 6. UTILITIES (Toasts & Dialogs) ---
        findViewById(R.id.btnDialog).setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                .setTitle("Confirm")
                .setMessage("Exit this page?")
                .setPositiveButton("Yes", (d, w) -> finish()) // finish() closes activity
                .setNegativeButton("No", null)
                .show();
        });

        // Simple Finish (Back)
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
    }
}
