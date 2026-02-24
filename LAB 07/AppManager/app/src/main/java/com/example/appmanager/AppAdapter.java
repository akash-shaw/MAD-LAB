package com.example.appmanager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class AppAdapter extends RecyclerView.Adapter<AppAdapter.ViewHolder> {

    Context context;
    List<AppModel> appList;

    public AppAdapter(Context context, List<AppModel> appList) {
        this.context = context;
        this.appList = appList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_app, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AppModel app = appList.get(position);
        holder.name.setText(app.name);
        holder.icon.setImageDrawable(app.icon);

        // Long Press Logic
        holder.itemView.setOnLongClickListener(v -> {
            showOptionsDialog(app);
            return true;
        });
    }

    @Override
    public int getItemCount() { return appList.size(); }

    private void showOptionsDialog(AppModel app) {
        String type = app.isSystem ? "System App" : "User Installed";
        // Check for special perms flag in MainActivity logic, or just navigate to details to see them.
        // For menu simplicity, we list actions.

        String[] options = {"Open App", "Uninstall", "View Details"};

        new AlertDialog.Builder(context)
                .setTitle(app.name + " (" + type + ")")
                .setItems(options, (dialog, which) -> {
                    if (which == 0) openApp(app.packageName);
                    if (which == 1) confirmUninstall(app.packageName);
                    if (which == 2) openDetails(app.packageName);
                })
                .show();
    }

    private void openApp(String packageName) {
        Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        if (launchIntent != null) context.startActivity(launchIntent);
        else Toast.makeText(context, "Cannot open this app", Toast.LENGTH_SHORT).show();
    }

    private void confirmUninstall(String packageName) {
        new AlertDialog.Builder(context)
                .setTitle("Confirm Uninstall")
                .setMessage("Are you sure you want to uninstall this app?")
                .setPositiveButton("Yes", (d, w) -> {
                    Intent intent = new Intent(Intent.ACTION_DELETE);
                    intent.setData(Uri.parse("package:" + packageName));
                    context.startActivity(intent);
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void openDetails(String packageName) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra("pkg", packageName);
        context.startActivity(intent);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.appIcon);
            name = itemView.findViewById(R.id.appName);
        }
    }
}