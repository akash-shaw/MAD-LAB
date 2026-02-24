package com.example.xyzfitness;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tvTitle, tvContent;
    private ImageView ivContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Init views
        tvTitle = findViewById(R.id.tvTitle);
        tvContent = findViewById(R.id.tvContent);
        ivContent = findViewById(R.id.ivContent);
    }

    // Inflate the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    // Handle clicks
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        ivContent.setVisibility(View.GONE); // Hide image by default

        if (id == R.id.action_home) {
            tvTitle.setText("XYZ Fitness Home");
            tvContent.setText("Welcome to XYZ Fitness Center.\nYour journey starts here.");
            return true;
        }
        else if (id == R.id.action_about) {
            tvTitle.setText("About Us");
            tvContent.setText("Established in 2024.\nWe are dedicated to your health.");
            return true;
        }
        else if (id == R.id.action_contact) {
            tvTitle.setText("Contact Us");
            tvContent.setText("Email: contact@xyzfitness.com\nPhone: +1 555-0199");
            return true;
        }
        else if (id == R.id.opt_workout) {
            tvTitle.setText("Workout Plans");
            tvContent.setText("1. Weight Loss Program\n   - High Intensity Interval Training\n   - Calorie Deficit Plan\n\n2. Cardio Blast\n   - Treadmill & Cycling\n   - Aerobics");
            return true;
        }
        else if (id == R.id.opt_trainers) {
            tvTitle.setText("Our Trainers");
            // Show image for trainers requirement
            ivContent.setVisibility(View.VISIBLE);
            ivContent.setImageResource(android.R.drawable.ic_menu_my_calendar); // Using system icon as placeholder photo
            tvContent.setText("Name: John Doe\nSpecialization: Strength Training\n\nName: Sarah Smith\nSpecialization: Yoga & Pilates");
            return true;
        }
        else if (id == R.id.opt_membership) {
            tvTitle.setText("Membership Packages");
            tvContent.setText("1. Gold Package - $50/mo\n   - All access\n   - Personal Trainer\n\n2. Silver Package - $30/mo\n   - Gym equipment only");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}