package com.example.examboilerplate;

import android.os.Bundle;
import android.widget.ToggleButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class ToggleFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toggle_fragment);

        ToggleButton toggle = findViewById(R.id.toggleFragmentBtn);

        // Initial Fragment
        replaceFragment(new ToggleOneFragment());

        toggle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                replaceFragment(new ToggleTwoFragment());
            } else {
                replaceFragment(new ToggleOneFragment());
            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.commit();
    }
}