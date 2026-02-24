package com.example.moviereviewapp;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DBHelper dbHelper;
    EditText etName, etYear, etRating;
    Button btnSave;
    ListView listView;
    TextView tvName, tvYear, tvRating;
    ArrayList<String> movieList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Init views
        dbHelper = new DBHelper(this);
        etName = findViewById(R.id.etName);
        etYear = findViewById(R.id.etYear);
        etRating = findViewById(R.id.etRating);
        btnSave = findViewById(R.id.btnSave);
        listView = findViewById(R.id.listView);
        tvName = findViewById(R.id.tvDetailName);
        tvYear = findViewById(R.id.tvDetailYear);
        tvRating = findViewById(R.id.tvDetailRating);

        movieList = new ArrayList<>();
        loadMovieList(); // Load initial data

        // Save Button Click
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String year = etYear.getText().toString();
                String rating = etRating.getText().toString();

                if(name.isEmpty() || year.isEmpty() || rating.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Enter all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (dbHelper.insertReview(name, year, rating)) {
                    Toast.makeText(MainActivity.this, "Saved!", Toast.LENGTH_SHORT).show();
                    etName.setText(""); etYear.setText(""); etRating.setText("");
                    loadMovieList(); // Refresh list
                } else {
                    Toast.makeText(MainActivity.this, "Error Saving", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // ListView Item Click
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedMovie = movieList.get(position);
                loadMovieDetails(selectedMovie);
            }
        });
    }

    // Helper to refresh ListView
    private void loadMovieList() {
        movieList.clear();
        Cursor cursor = dbHelper.getAllMovies();
        if (cursor.getCount() == 0) return;

        while (cursor.moveToNext()) {
            movieList.add(cursor.getString(1)); // Index 1 is Name
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, movieList);
        listView.setAdapter(adapter);
    }

    // Helper to show table details
    private void loadMovieDetails(String name) {
        Cursor cursor = dbHelper.getReviewDetails(name);
        if (cursor.moveToFirst()) {
            tvName.setText(cursor.getString(1));
            tvYear.setText(cursor.getString(2));
            tvRating.setText(cursor.getString(3));
        }
        cursor.close();
    }
}