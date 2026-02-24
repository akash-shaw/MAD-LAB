package com.example.menuassignment; // KEEP YOUR PACKAGE NAME HERE

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Declare the variable
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. Load the layout FIRST. If this is missing, app crashes.
        setContentView(R.layout.activity_main);

        // 2. Find the View by ID.
        imageView = findViewById(R.id.displayImageView);
    }

    // Load the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }

    // Handle menu clicks
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_image1) {
            imageView.setImageResource(R.drawable.image1);
            Toast.makeText(this, "Showing Image 1", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if (id == R.id.action_image2) {
            imageView.setImageResource(R.drawable.image2);
            Toast.makeText(this, "Showing Image 2", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}