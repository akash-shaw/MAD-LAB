package com.example.examboilerplate;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

public class ViewsActivity extends AppCompatActivity {

    // 1. Array containing your layout concepts and descriptions
    String[] layoutOptions = {
            "LinearLayout: Aligns children in a single row or column.",
            "RelativeLayout: Positions elements relative to each other/parent.",
            "ConstraintLayout: Flexible with constraints between elements.",
            "FrameLayout: Used for a single child view, often overlays.",
            "TableLayout: Arranges children into rows and columns."
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 2. Link to the views XML layout
        setContentView(R.layout.activity_views);

        // 3. Find list by ID
        ListView listView = findViewById(R.id.viewsListView);

        // 4. Create adapter to bridge data to the UI
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                layoutOptions
        );

        // 5. Set adapter
        listView.setAdapter(adapter);
    }
}