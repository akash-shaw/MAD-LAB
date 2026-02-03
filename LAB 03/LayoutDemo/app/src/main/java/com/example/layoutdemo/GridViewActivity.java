package com.example.layoutdemo;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class GridViewActivity extends AppCompatActivity {

    // Using built-in system icons for simplicity
    int[] images = {
            android.R.drawable.ic_menu_camera, android.R.drawable.ic_menu_gallery, android.R.drawable.ic_menu_compass,
            android.R.drawable.ic_menu_call, android.R.drawable.ic_menu_mapmode, android.R.drawable.ic_menu_my_calendar,
            android.R.drawable.ic_menu_camera, android.R.drawable.ic_menu_gallery, android.R.drawable.ic_menu_compass
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);

        GridView gridView = findViewById(R.id.myGridView);
        gridView.setAdapter(new ImageAdapter());
    }

    // Simple inner class adapter to display images
    private class ImageAdapter extends BaseAdapter {
        @Override
        public int getCount() { return images.length; }

        @Override
        public Object getItem(int position) { return images[position]; }

        @Override
        public long getItemId(int position) { return 0; }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView = new ImageView(getApplicationContext());
            imageView.setImageResource(images[position]);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(300, 300)); // Set image size
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return imageView;
        }
    }
}