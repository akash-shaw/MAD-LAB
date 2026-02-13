package com.example.vehicleparkingregistration;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Spinner spinnerType;
    EditText etVehicleNo, etRcNo;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Link views
        spinnerType = findViewById(R.id.spinnerVehicleType);
        etVehicleNo = findViewById(R.id.etVehicleNumber);
        etRcNo = findViewById(R.id.etRcNumber);
        btnSubmit = findViewById(R.id.btnSubmit);

        // Setup Spinner
        String[] types = {"Car", "Bike", "Truck", "Other"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, types);
        spinnerType.setAdapter(adapter);

        // Submit Logic
        btnSubmit.setOnClickListener(v -> {
            String type = spinnerType.getSelectedItem().toString();
            String vehicleNo = etVehicleNo.getText().toString();
            String rcNo = etRcNo.getText().toString();

            // Pass data to DetailsActivity
            Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
            intent.putExtra("TYPE", type);
            intent.putExtra("V_NO", vehicleNo);
            intent.putExtra("RC_NO", rcNo);
            startActivity(intent);
        });
    }
}