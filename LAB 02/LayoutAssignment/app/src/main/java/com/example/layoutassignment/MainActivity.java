package com.example.layoutassignment;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Scenario 1: Load the Linear Layout (Left Image)
        setContentView(R.layout.activity_main);

        // Scenario 2: To see the Relative Layout (Right Image),
        // setContentView(R.layout.activity_form);
    }
}