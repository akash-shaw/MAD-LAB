package com.example.groceryapp;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Spinner spinner;
    Button btnAdd;
    TextView tvTotal;
    ListView lvItems;
    DBHelper dbHelper;

    // Hardcoded grocery data
    String[] items = {"Apple ($2.00)", "Milk ($3.50)", "Bread ($1.50)", "Cheese ($5.00)"};
    double[] prices = {2.00, 3.50, 1.50, 5.00};

    // Lists for the ListView
    ArrayList<String> displayList; // Shows text
    ArrayList<Integer> idList;     // Stores DB IDs for deletion
    ArrayAdapter<String> listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Views
        spinner = findViewById(R.id.spinnerGrocery);
        btnAdd = findViewById(R.id.btnAdd);
        tvTotal = findViewById(R.id.tvTotal);
        lvItems = findViewById(R.id.lvItems);
        dbHelper = new DBHelper(this);

        // Setup Spinner
        ArrayAdapter<String> spinAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(spinAdapter);

        // Setup ListView
        displayList = new ArrayList<>();
        idList = new ArrayList<>();
        listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, displayList);
        lvItems.setAdapter(listAdapter);

        // Load initial data
        refreshData();

        // Add Button Logic
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = spinner.getSelectedItemPosition();
                if (dbHelper.insertItem(items[pos], prices[pos])) {
                    Toast.makeText(MainActivity.this, "Added", Toast.LENGTH_SHORT).show();
                    refreshData(); // Reload list and total
                }
            }
        });

        // ListView Click Logic (Delete)
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int dbId = idList.get(position); // Get DB ID
                dbHelper.deleteItem(dbId);       // Delete from DB
                Toast.makeText(MainActivity.this, "Item Removed", Toast.LENGTH_SHORT).show();
                refreshData();                   // Update UI
            }
        });
    }

    // Fetch data from DB and update UI
    private void refreshData() {
        displayList.clear();
        idList.clear();

        Cursor cursor = dbHelper.getAllItems();
        if (cursor.moveToFirst()) {
            do {
                // Get data from cursor (Column indexes: 0=id, 1=name, 2=cost)
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                double cost = cursor.getDouble(2);

                idList.add(id);
                displayList.add(name + " - $" + cost);
            } while (cursor.moveToNext());
        }
        cursor.close();
        listAdapter.notifyDataSetChanged();

        // Update Total
        tvTotal.setText("Total Cost: $" + dbHelper.getTotalCost());
    }
}