package com.example.textformattingdemo;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // connects this java file to the xml layout we just edited
        setContentView(R.layout.activity_main);
    }
}