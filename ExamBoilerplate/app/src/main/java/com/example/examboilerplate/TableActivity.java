package com.example.examboilerplate;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class TableActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        myDb = new DatabaseHelper(this);
        tableLayout = findViewById(R.id.displayTableLayout);

        displayDataInTable();
    }

    private void displayDataInTable() {
        Cursor cursor = myDb.getAllData();

        // Check if cursor has data
        if (cursor != null && cursor.moveToFirst()) {
            do {
                // 1. Create a new TableRow
                TableRow row = new TableRow(this);
                row.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                row.setPadding(8, 8, 8, 8);

                // 2. Create TextViews for columns
                TextView tvId = new TextView(this);
                tvId.setText(cursor.getString(0)); // ID column
                tvId.setPadding(4, 4, 4, 4);

                TextView tvName = new TextView(this);
                tvName.setText(cursor.getString(1)); // Name column
                tvName.setPadding(4, 4, 4, 4);

                // 3. Add TextViews to the Row
                row.addView(tvId);
                row.addView(tvName);

                // 4. Add the Row to the TableLayout
                tableLayout.addView(row);

                // Optional: Add a divider line programmatically
                android.view.View divider = new android.view.View(this);
                divider.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 1));
                divider.setBackgroundColor(Color.LTGRAY);
                tableLayout.addView(divider);

            } while (cursor.moveToNext());
            cursor.close();
        }
    }
}
