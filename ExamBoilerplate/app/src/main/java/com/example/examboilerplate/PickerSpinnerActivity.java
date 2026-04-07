package com.example.examboilerplate;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class PickerSpinnerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pickers_spinners);

        setupBasicSpinner();
        setupImageSpinner();
        setupPickers();
    }

    // 1. Basic Spinner
    private void setupBasicSpinner() {
        Spinner spinner = findViewById(R.id.basicSpinner);
        String[] items = {"Option A", "Option B", "Option C"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> p, View v, int pos, long id) {
                Toast.makeText(PickerSpinnerActivity.this, "Selected: " + items[pos], Toast.LENGTH_SHORT).show();
            }
            @Override public void onNothingSelected(AdapterView<?> p) {}
        });
    }

    // 2. Spinner with Images (Custom Adapter)
    private void setupImageSpinner() {
        Spinner spinner = findViewById(R.id.imageSpinner);
        String[] titles = {"Android", "Launcher", "App"};
        int[] images = {R.mipmap.ic_launcher, R.mipmap.ic_launcher_round, R.mipmap.ic_launcher};

        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(this, titles, images);
        spinner.setAdapter(adapter);
    }

    // 3. Date and Time Pickers
    private void setupPickers() {
        Button btnDate = findViewById(R.id.btnDatePicker);
        TextView tvDate = findViewById(R.id.tvSelectedDate);
        Button btnTime = findViewById(R.id.btnTimePicker);
        TextView tvTime = findViewById(R.id.tvSelectedTime);

        btnDate.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            DatePickerDialog dialog = new DatePickerDialog(this, (view, y, m, d) -> {
                String dateStr = d + "/" + (m + 1) + "/" + y;
                tvDate.setText("Date: " + dateStr);
                
                // Example Validation: Restrict to specific month
                if (m != Calendar.MAY) {
                   tvDate.append(" (Warning: Not in May!)");
                   tvDate.setTextColor(android.graphics.Color.RED);
                } else {
                   tvDate.setTextColor(android.graphics.Color.BLACK);
                }
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
            
            // Restrict: Min date is today
            dialog.getDatePicker().setMinDate(System.currentTimeMillis());
            dialog.show();
        });

        btnTime.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            TimePickerDialog dialog = new TimePickerDialog(this, (view, h, min) -> {
                String timeStr = h + ":" + String.format("%02d", min);
                tvTime.setText("Time: " + timeStr);

                // Example Error: Restrict to working hours (9-17)
                if (h < 9 || h > 17) {
                    Toast.makeText(this, "ERROR: Outside working hours!", Toast.LENGTH_LONG).show();
                    tvTime.setTextColor(android.graphics.Color.RED);
                } else {
                    tvTime.setTextColor(android.graphics.Color.BLACK);
                }
            }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true);
            dialog.show();
        });
    }

    // --- Custom Adapter for Spinner with Images ---
    private class CustomSpinnerAdapter extends ArrayAdapter<String> {
        private String[] titles;
        private int[] images;

        public CustomSpinnerAdapter(Context context, String[] titles, int[] images) {
            super(context, R.layout.spinner_item_with_image, titles);
            this.titles = titles;
            this.images = images;
        }

        @Override
        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_item_with_image, parent, false);
            }
            TextView text = convertView.findViewById(R.id.spinnerText);
            ImageView img = convertView.findViewById(R.id.spinnerImage);
            text.setText(titles[position]);
            img.setImageResource(images[position]);
            return convertView;
        }
    }
}
