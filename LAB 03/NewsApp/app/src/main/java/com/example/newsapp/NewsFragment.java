package com.example.newsapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class NewsFragment extends Fragment {

    private String sectionName;

    // Constructor to pass the category name (e.g., "Sports")
    public NewsFragment(String sectionName) {
        this.sectionName = sectionName;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the XML layout
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        // Set the text based on the section
        TextView textView = view.findViewById(R.id.sectionLabel);
        textView.setText(sectionName);

        return view;
    }
}