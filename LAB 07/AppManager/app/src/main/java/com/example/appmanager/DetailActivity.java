package com.example.appmanager;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;
import java.util.Arrays;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        String pkg = getIntent().getStringExtra("pkg");
        PackageManager pm = getPackageManager();

        try {
            // Get full package info including permissions
            PackageInfo pInfo = pm.getPackageInfo(pkg, PackageManager.GET_PERMISSIONS);
            ApplicationInfo appInfo = pInfo.applicationInfo;

            ImageView icon = findViewById(R.id.detailIcon);
            TextView name = findViewById(R.id.detailName);
            TextView version = findViewById(R.id.detailVersion);
            TextView size = findViewById(R.id.detailSize);
            TextView perms = findViewById(R.id.detailPermissions);

            icon.setImageDrawable(appInfo.loadIcon(pm));
            name.setText(appInfo.loadLabel(pm));
            version.setText("Version: " + pInfo.versionName);

            // Calculate APK size
            File file = new File(appInfo.publicSourceDir);
            long sizeInMb = file.length() / (1024 * 1024);
            size.setText("App Size: ~" + sizeInMb + " MB");

            // List Permissions & Check for Special ones
            StringBuilder sb = new StringBuilder();
            boolean hasCamera = false, hasLoc = false;

            if (pInfo.requestedPermissions != null) {
                for (String p : pInfo.requestedPermissions) {
                    sb.append(p).append("\n");
                    if (p.contains("CAMERA")) hasCamera = true;
                    if (p.contains("LOCATION")) hasLoc = true;
                }
            } else {
                sb.append("No permissions requested.");
            }

            perms.setText(sb.toString());

            // Optional: Show Toast for special permissions detected
            if (hasCamera || hasLoc) {
                String msg = "Special Access: " + (hasCamera ? "Camera " : "") + (hasLoc ? "Location" : "");
                android.widget.Toast.makeText(this, msg, android.widget.Toast.LENGTH_LONG).show();
            }

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}