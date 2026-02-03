package com.example.lifecycledemo;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "LifecycleEvent"; // Filter tag

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate invoked"); // App created
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart invoked"); // App visible
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume invoked"); // App interactive
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause invoked"); // Partially obscured or leaving
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop invoked"); // Fully backgrounded
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart invoked"); // Returning from stop
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy invoked"); // App closed
    }
}