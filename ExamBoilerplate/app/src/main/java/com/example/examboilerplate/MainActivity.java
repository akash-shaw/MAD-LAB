package com.example.examboilerplate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

/**
 * ---------------------------------------------------------
 * MAIN MENU ACTIVITY
 * ---------------------------------------------------------
 * WHAT THIS DOES:
 * 1. Acts as the central hub of the application.
 * 2. Uses a ListView to display all available boilerplate topics.
 * 3. Handles navigation between different activities based on list selection.
 *
 * KEY CONCEPTS:
 * - ArrayAdapter: Connects a simple array of Strings to a ListView UI.
 * - setOnItemClickListener: Detects which list item was clicked by the user.
 * - Intent: Used to switch from the current activity to a new one.
 */
public class MainActivity extends AppCompatActivity {

    // 1. TOPIC LIST: Add any new activity name here to show it in the main menu
    String[] appTopics = {
            "Views and Activities",
            "Interactive Components",
            "Toggle Fragments",
            "Spinners and Pickers",
            "App Bar and Menu",
            "Data Storage (SQLite & Prefs)",
            "Advanced SQL (Form & Table)",
            "TabLayout (Tabs)",
            "TableLayout",
            "GridView",
            "Blank Activity"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 2. TOOLBAR SETUP: Essential for showing the App Bar title
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Main Menu");
        }

        // 3. LISTVIEW & ADAPTER: Link the array to the UI list
        ListView listView = findViewById(R.id.mainListView);
        
        // ArrayAdapter takes (Context, Layout for row, Data)
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, appTopics);
        listView.setAdapter(adapter);

        // 4. NAVIGATION LOGIC: Map clicks to specific Activities
        listView.setOnItemClickListener((parent, view, position, id) -> {
            // Use if-else or switch based on the item index (0, 1, 2...)
            if (position == 0) startActivity(new Intent(this, ViewsActivity.class));
            else if (position == 1) startActivity(new Intent(this, InteractiveActivity.class));
            else if (position == 2) startActivity(new Intent(this, ToggleFragmentActivity.class));
            else if (position == 3) startActivity(new Intent(this, PickerSpinnerActivity.class));
            else if (position == 4) startActivity(new Intent(this, AppBarMenuActivity.class));
            else if (position == 5) startActivity(new Intent(this, StorageActivity.class));
            else if (position == 6) startActivity(new Intent(this, AdvancedSqlActivity.class));
            else if (position == 7) startActivity(new Intent(this, TabsActivity.class));
            else if (position == 8) startActivity(new Intent(this, TableActivity.class));
            else if (position == 9) startActivity(new Intent(this, GridActivity.class));
            else if (position == 10) startActivity(new Intent(this, BlankActivity.class));
        });
    }
}
