package com.example.newsapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStateAdapter {

    // Define your tab titles here
    private final String[] titles = new String[]{"Top Stories", "Sports", "Entertainment"};

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Create a new fragment for each tab, passing the title
        return new NewsFragment(titles[position]);
    }

    @Override
    public int getItemCount() {
        return titles.length; // Returns 3
    }
}