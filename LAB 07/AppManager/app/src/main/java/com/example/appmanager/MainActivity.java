package com.example.appmanager;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    AppAdapter adapter;
    List<AppModel> appList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadInstalledApps();
    }

    private void loadInstalledApps() {
        PackageManager pm = getPackageManager();
        // Get all packages
        List<PackageInfo> packages = pm.getInstalledPackages(PackageManager.GET_META_DATA);

        for (PackageInfo p : packages) {
            ApplicationInfo a = p.applicationInfo;

            // Check if System App
            boolean isSystem = (a.flags & ApplicationInfo.FLAG_SYSTEM) != 0;

            String name = a.loadLabel(pm).toString();
            AppModel app = new AppModel(name, p.packageName, a.loadIcon(pm), isSystem);

            appList.add(app);
        }

        adapter = new AppAdapter(this, appList);
        recyclerView.setAdapter(adapter);
    }

    // Refresh list if an app was uninstalled
    @Override
    protected void onResume() {
        super.onResume();
        appList.clear();
        loadInstalledApps();
    }
}