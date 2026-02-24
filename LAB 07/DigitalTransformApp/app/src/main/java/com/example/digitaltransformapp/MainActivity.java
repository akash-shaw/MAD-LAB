package com.example.digitaltransformapp;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView tvContent;
    private String fullText;
    private String currentKeyword = ""; // Stores last search for relevance sorting

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvContent = findViewById(R.id.tvContent);
        fullText = tvContent.getText().toString(); // Get initial text from XML
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_search) {
            showInputDialog("Search Keyword", input -> {
                currentKeyword = input;
                int count = countOccurrences(fullText, input);
                Toast.makeText(this, "Found " + count + " matches.", Toast.LENGTH_SHORT).show();
            });
            return true;
        } else if (id == R.id.action_highlight) {
            showInputDialog("Highlight Word", this::highlightText);
            return true;
        } else if (id == R.id.sort_alpha) {
            sortContent(true);
            return true;
        } else if (id == R.id.sort_relevance) {
            sortContent(false);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Generic dialog to get text input
    private void showInputDialog(String title, InputCallback callback) {
        EditText input = new EditText(this);
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setView(input)
                .setPositiveButton("OK", (dialog, which) -> callback.onInput(input.getText().toString()))
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void highlightText(String word) {
        if (word.isEmpty()) return;
        SpannableString spannable = new SpannableString(tvContent.getText());
        String text = spannable.toString().toLowerCase();
        String target = word.toLowerCase();
        int start = 0;

        // Clear previous highlights by resetting text
        tvContent.setText(fullText);
        spannable = new SpannableString(fullText);

        while ((start = text.indexOf(target, start)) >= 0) {
            spannable.setSpan(new BackgroundColorSpan(Color.YELLOW), start, start + target.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            start += target.length();
        }
        tvContent.setText(spannable);
    }

    private void sortContent(boolean alphabetical) {
        // Split by double newline to preserve paragraphs
        List<String> paragraphs = new ArrayList<>(Arrays.asList(fullText.split("\n\n")));

        if (alphabetical) {
            Collections.sort(paragraphs);
        } else {
            // Sort by occurrence count of the last searched keyword
            if (currentKeyword.isEmpty()) {
                Toast.makeText(this, "Search for a keyword first!", Toast.LENGTH_SHORT).show();
                return;
            }
            Collections.sort(paragraphs, (p1, p2) -> {
                int count1 = countOccurrences(p1, currentKeyword);
                int count2 = countOccurrences(p2, currentKeyword);
                return Integer.compare(count2, count1); // Descending order
            });
        }

        // Rejoin and display
        StringBuilder builder = new StringBuilder();
        for (String p : paragraphs) builder.append(p).append("\n\n");
        tvContent.setText(builder.toString().trim());
    }

    private int countOccurrences(String text, String keyword) {
        if (keyword.isEmpty()) return 0;
        return (text.toLowerCase().length() - text.toLowerCase().replace(keyword.toLowerCase(), "").length()) / keyword.length();
    }

    interface InputCallback {
        void onInput(String text);
    }
}