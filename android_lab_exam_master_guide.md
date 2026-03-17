# Android Lab Exam Master Guide (Java + XML) - Beginner Friendly

> **For MIT MAD Lab manual style questions**  
> **Goal:** Fast, exam-safe Android development using **Java + XML** only.

---

# 0. Golden Rules for the Exam

## Use this stack only
- **Language:** Java
- **UI:** XML layouts
- **Project type:** Empty Views Activity / Empty Activity
- **Do NOT use:** Kotlin, Jetpack Compose, RecyclerView unless forced

## Fastest exam strategy
- Prefer **single activity** unless question explicitly needs next screen
- If next screen needed, use **2 activities + explicit intent**
- Prefer **LinearLayout** unless RelativeLayout/ConstraintLayout is explicitly asked
- For menus, always create **res/menu/** XML first
- For lists/grids, prefer **ArrayAdapter** (not custom adapter) unless images are required
- For storage, prefer **SharedPreferences** if key-value is enough

## Things to do first in every question
1. Read what inputs are needed
2. Identify controls: EditText / Button / Spinner / CheckBox / RadioButton / ListView / etc.
3. Decide if 1 activity or 2 activities
4. Create XML first
5. Write Java: `setContentView()` -> `findViewById()` -> listeners -> logic
6. If 2nd activity: add it in **AndroidManifest.xml**
7. Run once early

---

# 1. Universal Android File Structure (What you will use most)

```text
app/
 ├── manifests/
 │    └── AndroidManifest.xml
 ├── java/
 │    └── com.example.appname/
 │         ├── MainActivity.java
 │         └── ResultActivity.java (if needed)
 ├── res/
 │    ├── layout/
 │    │    ├── activity_main.xml
 │    │    └── activity_result.xml
 │    ├── menu/
 │    │    ├── main_menu.xml
 │    │    ├── popup_menu.xml
 │    │    └── context_menu.xml
 │    ├── drawable/
 │    └── values/
 └── build.gradle
```

---

# 2. Universal Java Skeleton (MEMORIZE THIS)

```java
package com.example.myapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // findViewById here
        // listeners here
        // logic here
    }
}
```

---

# 3. Universal XML Skeleton (Fastest Safe Layout)

```xml
<?xml version="1.0" encoding="utf-8"?>
<ScrollView xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        android:padding="20dp">

        <!-- Put views here -->

    </LinearLayout>
</ScrollView>
```

> Use `ScrollView + LinearLayout` for long forms. Very safe.

---

# 4. Most Important Patterns (MEMORIZE)

## 4.1 findViewById
```java
EditText etName = findViewById(R.id.etName);
Button btnSubmit = findViewById(R.id.btnSubmit);
```

## 4.2 Button click
```java
btnSubmit.setOnClickListener(v -> {
    // action
});
```

## 4.3 Toast
```java
Toast.makeText(this, "Message", Toast.LENGTH_SHORT).show();
```

## 4.4 Get text from EditText
```java
String name = etName.getText().toString().trim();
```

## 4.5 Empty validation
```java
if (name.isEmpty()) {
    Toast.makeText(this, "Please enter name", Toast.LENGTH_SHORT).show();
    return;
}
```

## 4.6 Explicit Intent (open next activity)
```java
Intent intent = new Intent(MainActivity.this, ResultActivity.class);
intent.putExtra("key", value);
startActivity(intent);
```

## 4.7 Receive data in next activity
```java
String value = getIntent().getStringExtra("key");
```

## 4.8 Manifest entry for second activity
```xml
<activity android:name=".ResultActivity" />
<activity android:name=".MainActivity">
    <intent-filter>
        <action android:name="android.intent.action.MAIN" />
        <category android:name="android.intent.category.LAUNCHER" />
    </intent-filter>
</activity>
```

## 4.9 Implicit Intent (open URL)
```java
Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
startActivity(intent);
```

---

# 5. Lab 2 - Activity + Layouts (Most likely patterns)

## 5.1 Activity Lifecycle Demo

### Java
```java
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    TextView tvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvStatus = findViewById(R.id.tvStatus);
        tvStatus.setText("onCreate() called");
        Toast.makeText(this, "onCreate() called", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        tvStatus.setText("onStart() called");
        Toast.makeText(this, "onStart() called", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        tvStatus.setText("onResume() called");
        Toast.makeText(this, "onResume() called", Toast.LENGTH_SHORT).show();
    }
}
```

## 5.2 Calculator App (2 activities)

### MainActivity core logic
```java
private void calculateAndSend(String operator) {
    String s1 = etNum1.getText().toString().trim();
    String s2 = etNum2.getText().toString().trim();

    if (s1.isEmpty() || s2.isEmpty()) {
        Toast.makeText(this, "Please enter both numbers", Toast.LENGTH_SHORT).show();
        return;
    }

    double num1 = Double.parseDouble(s1);
    double num2 = Double.parseDouble(s2);
    double result = 0;

    switch (operator) {
        case "+": result = num1 + num2; break;
        case "-": result = num1 - num2; break;
        case "*": result = num1 * num2; break;
        case "/":
            if (num2 == 0) {
                Toast.makeText(this, "Division by zero not allowed", Toast.LENGTH_SHORT).show();
                return;
            }
            result = num1 / num2;
            break;
    }

    String finalResult = num1 + " " + operator + " " + num2 + " = " + result;

    Intent intent = new Intent(MainActivity.this, ResultActivity.class);
    intent.putExtra("result", finalResult);
    startActivity(intent);
}
```

### ResultActivity
```java
String result = getIntent().getStringExtra("result");
tvResult.setText(result);
```

## 5.3 URL Opener
```java
String url = etUrl.getText().toString().trim();

if (url.isEmpty()) {
    Toast.makeText(this, "Please enter a URL", Toast.LENGTH_SHORT).show();
    return;
}

if (!url.startsWith("http://") && !url.startsWith("https://")) {
    url = "https://" + url;
}

Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
startActivity(intent);
```

---

# 6. Lab 3 - ListView, GridView, TabLayout, TableLayout

## 6.1 ListView (Sports List) - VERY IMPORTANT

### Java
```java
ListView listViewSports = findViewById(R.id.listViewSports);
String[] sports = {"Cricket", "Football", "Basketball", "Tennis", "Hockey", "Badminton"};

ArrayAdapter<String> adapter = new ArrayAdapter<>(
        this,
        android.R.layout.simple_list_item_1,
        sports
);

listViewSports.setAdapter(adapter);

listViewSports.setOnItemClickListener((parent, view, position, id) -> {
    String selectedSport = sports[position];
    Toast.makeText(MainActivity.this, "Selected: " + selectedSport, Toast.LENGTH_SHORT).show();
});
```

## 6.2 GridView (Text-based, safest)
```java
GridView gridView = findViewById(R.id.gridViewItems);
String[] items = {"A", "B", "C", "D", "E", "F"};

ArrayAdapter<String> adapter = new ArrayAdapter<>(
        this,
        android.R.layout.simple_list_item_1,
        items
);

gridView.setAdapter(adapter);

gridView.setOnItemClickListener((parent, view, position, id) -> {
    Toast.makeText(this, "Clicked: " + items[position], Toast.LENGTH_SHORT).show();
});
```

## 6.3 TableLayout (mostly XML)
```xml
<TableLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:stretchColumns="1">

    <TableRow>
        <TextView android:text="Name" android:textStyle="bold" android:padding="8dp" />
        <TextView android:text="Branch" android:textStyle="bold" android:padding="8dp" />
        <TextView android:text="Year" android:textStyle="bold" android:padding="8dp" />
    </TableRow>

    <TableRow>
        <TextView android:text="Akash" android:padding="8dp" />
        <TextView android:text="CCE" android:padding="8dp" />
        <TextView android:text="3rd" android:padding="8dp" />
    </TableRow>
</TableLayout>
```

## 6.4 TabLayout + ViewPager (News App)

### activity_main.xml
```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <com.google.android.material.tabs.TabLayout
        android:id="@+id/tabLayout"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:tabMode="fixed"
        app:tabGravity="fill" />

    <androidx.viewpager.widget.ViewPager
        android:id="@+id/viewPager"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
</LinearLayout>
```

### MainActivity core
```java
TabLayout tabLayout = findViewById(R.id.tabLayout);
ViewPager viewPager = findViewById(R.id.viewPager);

ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
viewPager.setAdapter(adapter);

tabLayout.setupWithViewPager(viewPager);
```

### ViewPagerAdapter core
```java
public class ViewPagerAdapter extends FragmentPagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return TabFragment.newInstance("Top Stories Section");
            case 1: return TabFragment.newInstance("Sports Section");
            case 2: return TabFragment.newInstance("Entertainment Section");
            default: return TabFragment.newInstance("Other Section");
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0: return "Top Stories";
            case 1: return "Sports";
            case 2: return "Entertainment";
            default: return "";
        }
    }
}
```

### TabFragment core
```java
public static TabFragment newInstance(String content) {
    TabFragment fragment = new TabFragment();
    Bundle args = new Bundle();
    args.putString("content", content);
    fragment.setArguments(args);
    return fragment;
}

@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_tab, container, false);
    TextView tv = view.findViewById(R.id.tvTabContent);
    if (getArguments() != null) {
        tv.setText(getArguments().getString("content"));
    }
    return view;
}
```

---

# 7. Lab 4 - Input Controls (VERY IMPORTANT)

## 7.1 Food Ordering App (Most Important)

### CheckBox pattern
```java
StringBuilder orderDetails = new StringBuilder();
int total = 0;

if (cbPizza.isChecked()) {
    orderDetails.append("Pizza - ₹200\n");
    total += 200;
}

if (cbBurger.isChecked()) {
    orderDetails.append("Burger - ₹120\n");
    total += 120;
}

if (cbPasta.isChecked()) {
    orderDetails.append("Pasta - ₹150\n");
    total += 150;
}

if (cbCoke.isChecked()) {
    orderDetails.append("Coke - ₹50\n");
    total += 50;
}

if (total == 0) {
    Toast.makeText(this, "Please select at least one item", Toast.LENGTH_SHORT).show();
    return;
}

orderDetails.append("\nTotal Cost = ₹").append(total);

cbPizza.setEnabled(false);
cbBurger.setEnabled(false);
cbPasta.setEnabled(false);
cbCoke.setEnabled(false);
btnSubmit.setEnabled(false);

Intent intent = new Intent(MainActivity.this, ResultActivity.class);
intent.putExtra("order", orderDetails.toString());
startActivity(intent);
```

## 7.2 ToggleButton (Wi-Fi / Mobile Data)
```java
private void updateMode() {
    if (toggleMode.isChecked()) {
        imageMode.setImageResource(R.mipmap.ic_launcher);
        Toast.makeText(this, "Current Mode: Wi-Fi", Toast.LENGTH_SHORT).show();
    } else {
        imageMode.setImageResource(R.mipmap.ic_launcher);
        Toast.makeText(this, "Current Mode: Mobile Data", Toast.LENGTH_SHORT).show();
    }
}
```

## 7.3 RadioGroup + RadioButton
```java
int selectedId = radioGroupPayment.getCheckedRadioButtonId();

if (selectedId == -1) {
    Toast.makeText(this, "Please select a payment method", Toast.LENGTH_SHORT).show();
    return;
}

RadioButton selectedButton = findViewById(selectedId);
String selectedText = selectedButton.getText().toString();
Toast.makeText(this, "Selected: " + selectedText, Toast.LENGTH_SHORT).show();
```

## 7.4 SeekBar
```java
seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        tvValue.setText("Value: " + progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) { }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) { }
});
```

---

# 8. Lab 5 - Spinner + Pickers (VERY IMPORTANT)

## 8.1 Spinner setup (MEMORIZE)
```java
ArrayAdapter<String> adapter = new ArrayAdapter<>(
        this,
        android.R.layout.simple_spinner_item,
        items
);
adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
spinner.setAdapter(adapter);
```

## 8.2 Read selected spinner value
```java
String selected = spinner.getSelectedItem().toString();
```

## 8.3 DatePickerDialog
```java
Calendar c = Calendar.getInstance();
int y = c.get(Calendar.YEAR);
int m = c.get(Calendar.MONTH);
int d = c.get(Calendar.DAY_OF_MONTH);

DatePickerDialog dialog = new DatePickerDialog(this, (view, year, month, day) -> {
    etDate.setText(day + "/" + (month + 1) + "/" + year);
}, y, m, d);

dialog.show();
```

## 8.4 TimePickerDialog
```java
Calendar c = Calendar.getInstance();
int h = c.get(Calendar.HOUR_OF_DAY);
int min = c.get(Calendar.MINUTE);

TimePickerDialog dialog = new TimePickerDialog(this, (view, hour, minute) -> {
    etTime.setText(hour + ":" + minute);
}, h, min, true);

dialog.show();
```

## 8.5 Travel Ticket Booking App (Best full question)

### Submit core logic
```java
String source = spinnerSource.getSelectedItem().toString();
String destination = spinnerDestination.getSelectedItem().toString();
String date = etDate.getText().toString().trim();
String tripType = toggleTripType.isChecked() ? "Round Trip" : "One Way";

if (source.equals(destination)) {
    Toast.makeText(this, "Source and destination cannot be same", Toast.LENGTH_SHORT).show();
    return;
}

if (date.isEmpty()) {
    Toast.makeText(this, "Please select a date", Toast.LENGTH_SHORT).show();
    return;
}

String details = "Source: " + source +
        "\nDestination: " + destination +
        "\nTravel Date: " + date +
        "\nTrip Type: " + tripType;

Intent intent = new Intent(MainActivity.this, ResultActivity.class);
intent.putExtra("details", details);
startActivity(intent);
```

### Reset core logic
```java
spinnerSource.setSelection(0);
spinnerDestination.setSelection(0);
toggleTripType.setChecked(false);
setCurrentDate();
Toast.makeText(this, "Form reset", Toast.LENGTH_SHORT).show();
```

## 8.6 Premium booking after 12 PM condition (Movie Ticket)
```java
Calendar calendar = Calendar.getInstance();
int currentHour = calendar.get(Calendar.HOUR_OF_DAY);

if (togglePremium.isChecked() && currentHour < 12) {
    Toast.makeText(this, "Premium booking allowed only after 12:00 PM", Toast.LENGTH_SHORT).show();
    return;
}
```

---

# 9. Lab 6 - Options Menu + Toolbar

## 9.1 Create menu XML (res/menu/main_menu.xml)
```xml
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android">

    <item
        android:id="@+id/menu_workout"
        android:title="Workout Plans" />

    <item
        android:id="@+id/menu_trainers"
        android:title="Trainers" />

    <item
        android:id="@+id/menu_membership"
        android:title="Membership" />

</menu>
```

## 9.2 Inflate menu (MEMORIZE)
```java
@Override
public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main_menu, menu);
    return true;
}
```

## 9.3 Handle menu clicks (MEMORIZE)
```java
@Override
public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    int id = item.getItemId();

    if (id == R.id.menu_workout) {
        tvContent.setText("Workout Plans:\n1. Weight Loss\n2. Cardio\n3. Strength Training");
        return true;
    } else if (id == R.id.menu_trainers) {
        tvContent.setText("Trainers:\n1. Rahul - Cardio Expert\n2. Sneha - Yoga Coach");
        return true;
    } else if (id == R.id.menu_membership) {
        tvContent.setText("Membership:\nBasic - ₹999/month\nPremium - ₹1999/month");
        return true;
    }

    return super.onOptionsItemSelected(item);
}
```

## 9.4 Toolbar as App Bar

### XML
```xml
<androidx.appcompat.widget.Toolbar
    android:id="@+id/toolbar"
    android:layout_width="match_parent"
    android:layout_height="?attr/actionBarSize"
    android:background="?attr/colorPrimary"
    app:title="XYZ Fitness Center"
    app:titleTextColor="@android:color/white" />
```

### Java
```java
Toolbar toolbar = findViewById(R.id.toolbar);
setSupportActionBar(toolbar);
```

---

# 10. Lab 7 - Context Menu + PopupMenu

## 10.1 Context Menu (Long Press)

### Register view
```java
registerForContextMenu(tvMessage);
```

### Create menu
```java
@Override
public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
    super.onCreateContextMenu(menu, v, menuInfo);
    getMenuInflater().inflate(R.menu.context_menu, menu);
    menu.setHeaderTitle("Choose an option");
}
```

### Handle click
```java
@Override
public boolean onContextItemSelected(@NonNull MenuItem item) {
    int id = item.getItemId();

    if (id == R.id.menu_edit) {
        Toast.makeText(this, "Edit selected", Toast.LENGTH_SHORT).show();
        return true;
    } else if (id == R.id.menu_delete) {
        Toast.makeText(this, "Delete selected", Toast.LENGTH_SHORT).show();
        return true;
    } else if (id == R.id.menu_share) {
        Toast.makeText(this, "Share selected", Toast.LENGTH_SHORT).show();
        return true;
    }

    return super.onContextItemSelected(item);
}
```

## 10.2 PopupMenu (MOST IMPORTANT)
```java
btnPopup.setOnClickListener(v -> {
    PopupMenu popupMenu = new PopupMenu(MainActivity.this, btnPopup);
    popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());

    popupMenu.setOnMenuItemClickListener(item -> {
        int id = item.getItemId();

        if (id == R.id.option_one) {
            Toast.makeText(MainActivity.this, "Option One Selected", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.option_two) {
            Toast.makeText(MainActivity.this, "Option Two Selected", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.option_three) {
            Toast.makeText(MainActivity.this, "Option Three Selected", Toast.LENGTH_SHORT).show();
            return true;
        }

        return false;
    });

    popupMenu.show();
});
```

## 10.3 ImageButton + PopupMenu (My Menu)
```java
btnImageMenu.setOnClickListener(v -> {
    PopupMenu popupMenu = new PopupMenu(MainActivity.this, btnImageMenu);
    popupMenu.getMenuInflater().inflate(R.menu.image_menu, popupMenu.getMenu());

    popupMenu.setOnMenuItemClickListener(item -> {
        int id = item.getItemId();

        if (id == R.id.menu_image1) {
            imageViewDisplay.setImageResource(R.mipmap.ic_launcher);
            Toast.makeText(this, "Image-1 displayed", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.menu_image2) {
            imageViewDisplay.setImageResource(R.mipmap.ic_launcher);
            Toast.makeText(this, "Image-2 displayed", Toast.LENGTH_SHORT).show();
            return true;
        }

        return false;
    });

    popupMenu.show();
});
```

---

# 11. Lab 8 - SharedPreferences + SQLite (HIGH VALUE)

> Your manual table of contents includes **SQLite and Shared Preferences** as Lab 8. fileciteturn0file0

## 11.1 SharedPreferences (EASIEST storage)

### Save data
```java
SharedPreferences sp = getSharedPreferences("MyPrefs", MODE_PRIVATE);
SharedPreferences.Editor editor = sp.edit();
editor.putString("name", name);
editor.putString("email", email);
editor.apply();
```

### Read data
```java
SharedPreferences sp = getSharedPreferences("MyPrefs", MODE_PRIVATE);
String savedName = sp.getString("name", "No Name");
String savedEmail = sp.getString("email", "No Email");
```

### Clear data
```java
SharedPreferences sp = getSharedPreferences("MyPrefs", MODE_PRIVATE);
sp.edit().clear().apply();
```

## 11.2 Simple SharedPreferences App Pattern
- 2 EditText: Name, Email
- 3 Buttons: Save, Load, Clear
- 1 TextView: show output

### Java core
```java
btnSave.setOnClickListener(v -> {
    String name = etName.getText().toString().trim();
    String email = etEmail.getText().toString().trim();

    if (name.isEmpty() || email.isEmpty()) {
        Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        return;
    }

    SharedPreferences sp = getSharedPreferences("MyPrefs", MODE_PRIVATE);
    SharedPreferences.Editor editor = sp.edit();
    editor.putString("name", name);
    editor.putString("email", email);
    editor.apply();

    Toast.makeText(this, "Data Saved", Toast.LENGTH_SHORT).show();
});

btnLoad.setOnClickListener(v -> {
    SharedPreferences sp = getSharedPreferences("MyPrefs", MODE_PRIVATE);
    String savedName = sp.getString("name", "No Name");
    String savedEmail = sp.getString("email", "No Email");

    tvResult.setText("Name: " + savedName + "\nEmail: " + savedEmail);
});

btnClear.setOnClickListener(v -> {
    SharedPreferences sp = getSharedPreferences("MyPrefs", MODE_PRIVATE);
    sp.edit().clear().apply();
    tvResult.setText("");
    Toast.makeText(this, "Data Cleared", Toast.LENGTH_SHORT).show();
});
```

## 11.3 SQLite - Full Beginner Safe Pattern

### Step 1: DBHelper.java
```java
package com.example.sqliteapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "StudentDB";
    public static final int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE students (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, branch TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS students");
        onCreate(db);
    }

    public boolean insertStudent(String name, String branch) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("branch", branch);
        long result = db.insert("students", null, cv);
        return result != -1;
    }

    public Cursor getAllStudents() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM students", null);
    }

    public boolean updateStudent(String id, String name, String branch) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("branch", branch);
        int rows = db.update("students", cv, "id=?", new String[]{id});
        return rows > 0;
    }

    public boolean deleteStudent(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rows = db.delete("students", "id=?", new String[]{id});
        return rows > 0;
    }
}
```

## 11.4 SQLite CRUD App Pattern (Recommended exam pattern)
- 3 EditText: id, name, branch
- 4 Buttons: Insert, View, Update, Delete
- 1 TextView: output

### MainActivity core
```java
DBHelper dbHelper = new DBHelper(this);

btnInsert.setOnClickListener(v -> {
    String name = etName.getText().toString().trim();
    String branch = etBranch.getText().toString().trim();

    if (name.isEmpty() || branch.isEmpty()) {
        Toast.makeText(this, "Enter name and branch", Toast.LENGTH_SHORT).show();
        return;
    }

    boolean inserted = dbHelper.insertStudent(name, branch);
    Toast.makeText(this, inserted ? "Inserted Successfully" : "Insert Failed", Toast.LENGTH_SHORT).show();
});

btnView.setOnClickListener(v -> {
    Cursor cursor = dbHelper.getAllStudents();
    StringBuilder sb = new StringBuilder();

    if (cursor.getCount() == 0) {
        tvResult.setText("No records found");
        return;
    }

    while (cursor.moveToNext()) {
        sb.append("ID: ").append(cursor.getString(0)).append("\n");
        sb.append("Name: ").append(cursor.getString(1)).append("\n");
        sb.append("Branch: ").append(cursor.getString(2)).append("\n\n");
    }

    tvResult.setText(sb.toString());
});

btnUpdate.setOnClickListener(v -> {
    String id = etId.getText().toString().trim();
    String name = etName.getText().toString().trim();
    String branch = etBranch.getText().toString().trim();

    if (id.isEmpty() || name.isEmpty() || branch.isEmpty()) {
        Toast.makeText(this, "Enter id, name, and branch", Toast.LENGTH_SHORT).show();
        return;
    }

    boolean updated = dbHelper.updateStudent(id, name, branch);
    Toast.makeText(this, updated ? "Updated Successfully" : "Update Failed", Toast.LENGTH_SHORT).show();
});

btnDelete.setOnClickListener(v -> {
    String id = etId.getText().toString().trim();

    if (id.isEmpty()) {
        Toast.makeText(this, "Enter id", Toast.LENGTH_SHORT).show();
        return;
    }

    boolean deleted = dbHelper.deleteStudent(id);
    Toast.makeText(this, deleted ? "Deleted Successfully" : "Delete Failed", Toast.LENGTH_SHORT).show();
});
```

### SQLite XML skeleton (fast)
```xml
<ScrollView xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        android:padding="20dp">

        <EditText android:id="@+id/etId" android:layout_width="match_parent" android:layout_height="wrap_content" android:hint="Enter ID for Update/Delete" />
        <EditText android:id="@+id/etName" android:layout_width="match_parent" android:layout_height="wrap_content" android:hint="Enter Name" android:layout_marginTop="10dp" />
        <EditText android:id="@+id/etBranch" android:layout_width="match_parent" android:layout_height="wrap_content" android:hint="Enter Branch" android:layout_marginTop="10dp" />

        <Button android:id="@+id/btnInsert" android:layout_width="match_parent" android:layout_height="wrap_content" android:text="Insert" android:layout_marginTop="12dp" />
        <Button android:id="@+id/btnView" android:layout_width="match_parent" android:layout_height="wrap_content" android:text="View All" android:layout_marginTop="8dp" />
        <Button android:id="@+id/btnUpdate" android:layout_width="match_parent" android:layout_height="wrap_content" android:text="Update" android:layout_marginTop="8dp" />
        <Button android:id="@+id/btnDelete" android:layout_width="match_parent" android:layout_height="wrap_content" android:text="Delete" android:layout_marginTop="8dp" />

        <TextView android:id="@+id/tvResult" android:layout_width="match_parent" android:layout_height="wrap_content" android:text="Output" android:textSize="18sp" android:layout_marginTop="20dp" />

    </LinearLayout>
</ScrollView>
```

---

# 12. Permission Snippets (Only if needed)

## Camera permission (if asked later)
```xml
<uses-permission android:name="android.permission.CAMERA" />
```

## Bluetooth (basic, older style)
```xml
<uses-permission android:name="android.permission.BLUETOOTH" />
<uses-permission android:name="android.permission.BLUETOOTH_ADMIN" />
```

> For your exam, if project-based labs appear, they may ask concepts. But for short coding tests, these are less likely than the earlier labs.

---

# 13. Ready-to-use XML Controls Cheat Sheet

## EditText
```xml
<EditText
    android:id="@+id/etName"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:hint="Enter Name" />
```

## Button
```xml
<Button
    android:id="@+id/btnSubmit"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="Submit" />
```

## TextView
```xml
<TextView
    android:id="@+id/tvResult"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="Result"
    android:textSize="20sp"
    android:textStyle="bold" />
```

## CheckBox
```xml
<CheckBox
    android:id="@+id/cbPizza"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="Pizza - ₹200" />
```

## RadioGroup + RadioButton
```xml
<RadioGroup
    android:id="@+id/radioGroupPayment"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content">

    <RadioButton
        android:id="@+id/rbCash"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Cash" />

    <RadioButton
        android:id="@+id/rbCard"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Card" />
</RadioGroup>
```

## ToggleButton
```xml
<ToggleButton
    android:id="@+id/toggleTripType"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:textOn="Round Trip"
    android:textOff="One Way" />
```

## Spinner
```xml
<Spinner
    android:id="@+id/spinnerSource"
    android:layout_width="match_parent"
    android:layout_height="wrap_content" />
```

## ListView
```xml
<ListView
    android:id="@+id/listViewSports"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```

## GridView
```xml
<GridView
    android:id="@+id/gridViewItems"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:numColumns="3" />
```

## SeekBar
```xml
<SeekBar
    android:id="@+id/seekBar"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:max="100" />
```

## ImageView
```xml
<ImageView
    android:id="@+id/imageViewDisplay"
    android:layout_width="150dp"
    android:layout_height="150dp"
    android:src="@mipmap/ic_launcher" />
```

---

# 14. Common Imports You Will Need Often

## Basic UI + Toast + Intent
```java
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
```

## Menus
```java
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
```

## Date / Time Picker
```java
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import java.util.Calendar;
```

## SQLite
```java
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
```

## Fragments + Tabs
```java
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
```

---

# 15. Most Common Exam Mistakes (Read before submitting)

## Activity / Intent mistakes
- Forgot to add `ResultActivity` in **AndroidManifest.xml**
- Wrong key in `putExtra()` / `getStringExtra()`
- Forgot `setContentView()` before `findViewById()`

## XML mistakes
- Wrong `id`
- Missing closing tags
- Put menu XML inside `layout` instead of `menu`

## Input mistakes
- Didn’t validate empty fields
- Didn’t handle division by zero
- Didn’t check if no checkbox selected
- Didn’t check if no RadioButton selected
- Didn’t check if source = destination

## Menu mistakes
- Forgot `popupMenu.show()`
- Forgot `registerForContextMenu()`
- Wrong menu resource name in `inflate()`

## Storage mistakes
- Used wrong SharedPreferences file name/key
- Forgot to create `DBHelper`
- Forgot `onCreate()` table creation in SQLite
- Forgot `cursor.moveToNext()` loop

---

# 16. What to Do if You Panic in the Exam

## If question looks big
Reduce it to these patterns:

- **Form app** -> EditText + Button + TextView / ResultActivity
- **Selection app** -> CheckBox or RadioGroup
- **Dropdown app** -> Spinner
- **Date/time app** -> DatePickerDialog / TimePickerDialog
- **List app** -> ListView + ArrayAdapter
- **Grid app** -> GridView + ArrayAdapter
- **Menu app** -> PopupMenu or Options Menu
- **Storage app** -> SharedPreferences first, SQLite if explicitly asked

## If image is asked but you don’t have assets
- Use `@mipmap/ic_launcher` temporarily
- Make logic work first

## If icons are asked in menu
- Make text menu first
- Add icons only if time remains

## If question says “display details”
Fastest safe options:
1. Change a `TextView`
2. Open `ResultActivity`
3. Use `Toast` for short text only

---

# 17. Best Priority Order During Exam (If You Can Choose)

If multiple questions appear, choose in this order:

1. **Food Ordering App (CheckBox + Intent)**
2. **Travel Ticket Booking (Spinner + DatePicker + Intent)**
3. **PopupMenu / Options Menu app**
4. **ListView sports app**
5. **URL opener**
6. **SharedPreferences save/load app**
7. **SQLite CRUD app** (only if comfortable)
8. **TabLayout** (only if you have time)

---

# 18. Ultra-Short Viva-Free Theory (Only for understanding while coding)

- **Activity** = one screen
- **XML layout** = UI structure
- **Java file** = logic
- **Intent** = move/open
- **Explicit Intent** = your own app screen
- **Implicit Intent** = external app (browser, etc.)
- **Adapter** = puts array data into ListView/GridView
- **Spinner** = dropdown
- **SharedPreferences** = simple key-value storage
- **SQLite** = local database

---

# 19. Final 10-Minute Revision Checklist Before Exam Starts

## Memorize these exact patterns:
- `setContentView(R.layout.activity_main);`
- `findViewById(R.id.someId);`
- `btn.setOnClickListener(v -> { ... });`
- `Toast.makeText(this, "...", Toast.LENGTH_SHORT).show();`
- `Intent intent = new Intent(MainActivity.this, ResultActivity.class);`
- `intent.putExtra("key", value);`
- `getIntent().getStringExtra("key");`
- Spinner `ArrayAdapter`
- `DatePickerDialog`
- `PopupMenu`
- `onCreateOptionsMenu()`
- `onOptionsItemSelected()`
- SharedPreferences save/load
- SQLite `insert + view`

---

# 20. Final Advice for You (Most Important)

Since you said you’re a beginner:

## In the exam, do NOT try to be fancy.
Do the **simplest working version**.

### Faculty usually rewards:
- correct UI controls
- correct logic
- working output
- validation
- clean navigation

### Faculty usually does NOT care if:
- UI is super beautiful
- icons are custom
- animations are fancy
- code is enterprise-level

## Your winning formula:
**Simple + Working + No Crash + Matches question**

---

# 21. My Recommended Must-Practice Set (If you revise only 6 things)

1. **Calculator with ResultActivity**
2. **Sports ListView + Toast**
3. **Food Ordering App**
4. **Travel Ticket Booking App**
5. **Options Menu (Fitness Center)**
6. **PopupMenu + Context Menu**
7. **SharedPreferences Save/Load**
8. **SQLite CRUD (Insert + View minimum)**

---

# 22. Emergency Copy-Paste Patterns (Fast Rescue)

## Save text in TextView
```java
tvResult.setText("Some output here");
```

## Disable controls after submit
```java
btnSubmit.setEnabled(false);
cbPizza.setEnabled(false);
```

## Read ToggleButton state
```java
String tripType = toggleTripType.isChecked() ? "Round Trip" : "One Way";
```

## Read CheckBox state
```java
if (cbPizza.isChecked()) { ... }
```

## Read RadioButton selection
```java
int selectedId = radioGroup.getCheckedRadioButtonId();
RadioButton rb = findViewById(selectedId);
String selected = rb.getText().toString();
```

## Read spinner value
```java
String selected = spinner.getSelectedItem().toString();
```

## Show selected list item
```java
listView.setOnItemClickListener((parent, view, position, id) -> {
    Toast.makeText(this, items[position], Toast.LENGTH_SHORT).show();
});
```

---

# 23. If You Want to Add More Later

You can extend this file later with:
- Lab 9 project topics (Bluetooth, Camera, Broadcast)
- custom Toast with image
- custom Spinner with image
- installed apps list (simplified)
- more SQLite query variations

---

# DONE

**Use this as your exam cheat sheet / quick reference.**

If you want, next I can make you a **SUPER SHORT “2-page last-minute cheat sheet”** version of this same file, where I keep only the exact code snippets you’ll likely copy during the exam.

