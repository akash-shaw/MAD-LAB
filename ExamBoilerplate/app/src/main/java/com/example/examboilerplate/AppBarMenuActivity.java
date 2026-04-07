package com.example.examboilerplate;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AppBarMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_bar_menu);
        
        // --- 0. Setup Toolbar ---
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar); // This makes the toolbar act as the ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Menu Boilerplate");
        }

        // --- 2. Popup Menu Setup ---
        Button btnPopup = findViewById(R.id.btnShowPopup);
        btnPopup.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(this, v);
            popup.getMenuInflater().inflate(R.menu.app_bar_menu, popup.getMenu());
            popup.setOnMenuItemClickListener(item -> {
                handleMenuAction(item);
                return true;
            });
            popup.show();
        });

        // --- 3. Context Menu Setup ---
        TextView tvContext = findViewById(R.id.tvContextMenu);
        registerForContextMenu(tvContext);
    }

    // --- 1. Options Menu (App Bar) ---
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_bar_menu, menu);
        
        // Reflection to show icons in overflow
        if (menu != null && menu.getClass().getSimpleName().equals("MenuBuilder")) {
            try {
                java.lang.reflect.Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", boolean.class);
                m.setAccessible(true);
                m.invoke(menu, true);
            } catch (Exception e) {}
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        handleMenuAction(item);
        return true;
    }

    // --- 3. Context Menu Callbacks ---
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo info) {
        super.onCreateContextMenu(menu, v, info);
        getMenuInflater().inflate(R.menu.app_bar_menu, menu);
        menu.setHeaderTitle("Context Menu Options");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        handleMenuAction(item);
        return true;
    }

    private void handleMenuAction(MenuItem item) {
        int id = item.getItemId();
        String title = item.getTitle().toString();
        
        if (id == R.id.action_search) {
            Toast.makeText(this, "Searching...", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.action_share) {
            Toast.makeText(this, "Sharing...", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.action_exit) {
            finish();
        } else {
            // General handler for others (Settings, About, Feedback, etc.)
            Toast.makeText(this, "Selected: " + title, Toast.LENGTH_SHORT).show();
        }
    }
}
