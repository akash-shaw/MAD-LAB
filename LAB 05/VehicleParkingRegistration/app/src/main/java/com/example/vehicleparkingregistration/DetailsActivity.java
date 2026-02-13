package com.example.vehicleparkingregistration;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.UUID;

public class DetailsActivity extends AppCompatActivity {

    TextView tvDetails;
    Button btnConfirm, btnEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
S
        // Link views
        tvDetails = findViewById(R.id.tvDisplayDetails);
        btnConfirm = findViewById(R.id.btnConfirm);
        btnEdit = findViewById(R.id.btnEdit);

        // Get data from intent
        String type = getIntent().getStringExtra("TYPE");
        String vNo = getIntent().getStringExtra("V_NO");
        String rcNo = getIntent().getStringExtra("RC_NO");

        // Show data
        tvDetails.setText("Type: " + type + "\nVehicle No: " + vNo + "\nRC No: " + rcNo);

        // Edit Action: Close this activity to go back
        btnEdit.setOnClickListener(v -> finish());

        // Confirm Action: Generate ID and show Toast
        btnConfirm.setOnClickListener(v -> {
            String serial = "SN-" + UUID.randomUUID().toString().substring(0, 5).toUpperCase();
            Toast.makeText(this, "Allocated! Serial: " + serial, Toast.LENGTH_LONG).show();
        });
    }
}