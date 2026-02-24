package com.example.taskmanagerapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DBHelper db;
    ListView listView;
    ArrayList<TaskModel> taskList; // Use a helper class for cleaner data handling
    TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DBHelper(this);
        listView = findViewById(R.id.listViewTasks);
        taskList = new ArrayList<>();
        adapter = new TaskAdapter();
        listView.setAdapter(adapter);

        findViewById(R.id.fabAdd).setOnClickListener(v -> startActivity(new Intent(this, FormActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        taskList.clear();
        Cursor cursor = db.getAllTasks();
        if (cursor.moveToFirst()) {
            do {
                taskList.add(new TaskModel(
                        cursor.getString(0), // ID
                        cursor.getString(1), // Name
                        cursor.getLong(2),   // Date (TimeStamp)
                        cursor.getString(3)  // Priority
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        adapter.notifyDataSetChanged();
    }

    // Helper class to hold data
    static class TaskModel {
        String id, name, priority;
        long date;
        TaskModel(String id, String name, long date, String priority) {
            this.id = id; this.name = name; this.date = date; this.priority = priority;
        }
    }

    class TaskAdapter extends BaseAdapter {
        @Override public int getCount() { return taskList.size(); }
        @Override public Object getItem(int i) { return taskList.get(i); }
        @Override public long getItemId(int i) { return i; }

        @Override
        public View getView(int i, View view, ViewGroup parent) {
            if (view == null) view = getLayoutInflater().inflate(R.layout.task_item, parent, false);

            TextView name = view.findViewById(R.id.tvName);
            TextView date = view.findViewById(R.id.tvDate);
            TextView priority = view.findViewById(R.id.tvPriority);
            ImageButton edit = view.findViewById(R.id.btnEdit);
            ImageButton delete = view.findViewById(R.id.btnDelete);

            TaskModel task = taskList.get(i);

            name.setText(task.name);
            // Convert timestamp to readable string
            date.setText("Due: " + DateFormat.format("dd/MM/yyyy HH:mm", task.date));
            priority.setText("Priority: " + task.priority);

            edit.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, FormActivity.class);
                intent.putExtra("id", task.id);
                intent.putExtra("name", task.name);
                intent.putExtra("date", task.date); // Pass Long
                intent.putExtra("priority", task.priority);
                startActivity(intent);
            });

            delete.setOnClickListener(v -> {
                db.deleteTask(task.id);
                loadData();
                Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
            });

            return view;
        }
    }
}