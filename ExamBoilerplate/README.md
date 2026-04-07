# 🎓 Android Exam Boilerplate Guide

This project contains comprehensive boilerplates for common Android development tasks required in practical exams. Each section below explains how to implement the features from scratch, including UI, XML, and Java code.

---

## 📋 Table of Contents
1. [General Setup & Back Button](#1-general-setup--back-navigation)
2. [Interactive Components & Visibility](#2-interactive-components)
3. [App Bar & Menus (Detailed)](#3-app-bar--menus-detailed)
4. [Spinners (Basic & Custom with Images)](#4-spinners)
5. [Pickers (Date & Time)](#5-pickers)
6. [Data Storage (SQLite & SharedPrefs)](#6-data-storage)
7. [Fragments (How to Create & Use)](#7-fragments-how-to-create--use)
8. [Dynamic TableLayout](#8-tablelayout)

---

## 1. General Setup & Back Navigation
### Adding a New Activity
1. **Layout**: Create a new XML in `res/layout/`. Use `android:fitsSystemWindows="true"` on the root container.
2. **Java**: Create a class extending `AppCompatActivity`. Call `setContentView(R.layout.your_layout)`.
3. **Manifest**: Register in `AndroidManifest.xml` inside `<application>`:
   ```xml
   <activity android:name=".YourActivityName" android:exported="false" />
   ```

### Implementing Back Button
- **Method 1 (In Code)**: To make a button close the page and go back.
  ```java
  findViewById(R.id.btnBack).setOnClickListener(v -> finish());
  ```
- **Method 2 (App Bar Back)**: To show a back arrow in the top left.
  1. In `AndroidManifest.xml`, add `parentActivityName`:
     ```xml
     <activity android:name=".DetailActivity" android:parentActivityName=".MainActivity" />
     ```
  2. In `onCreate`:
     ```java
     getSupportActionBar().setDisplayHomeAsUpEnabled(true);
     ```

---

## 2. Interactive Components
### Visibility Logic
To hide/show an element:
- **Java**:
  ```java
  View v = findViewById(R.id.targetView);
  v.setVisibility(View.GONE);      // Hidden + Takes no space
  v.setVisibility(View.INVISIBLE); // Hidden + Takes space
  v.setVisibility(View.VISIBLE);   // Visible
  ```

---

## 3. App Bar & Menus (Detailed)
### Step 1: Create the Menu XML
1. Right-click `res` folder -> **New** -> **Android Resource Directory**. Select **Resource type: menu**.
2. Right-click the new `menu` folder -> **New** -> **Menu Resource File** (e.g., `my_menu.xml`).
3. Add items:
   ```xml
   <menu xmlns:android="http://schemas.android.com/apk/res/android" xmlns:app="http://schemas.android.com/apk/res-auto">
       <item android:id="@+id/action_search" android:icon="@drawable/ic_search" android:title="Search" app:showAsAction="ifRoom" />
       <item android:id="@+id/action_settings" android:title="Settings" app:showAsAction="never" />
   </menu>
   ```

### Step 2: Add App Bar (Toolbar) to Layout
If your theme is `NoActionBar`, add this to your Activity XML:
```xml
<androidx.appcompat.widget.Toolbar
    android:id="@+id/toolbar"
    android:layout_width="match_parent"
    android:layout_height="?attr/actionBarSize"
    android:background="?attr/colorPrimary" />
```

### Step 3: Link in Java
```java
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_layout);
    
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar); // Link Toolbar to Activity
}

@Override
public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.my_menu, menu); // Display the menu
    return true;
}

@Override
public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.action_search) {
        // Handle search click
        return true;
    }
    return super.onOptionsItemSelected(item);
}
```

---

## 4. Spinners
### Basic Spinner
- **Java**:
  ```java
  String[] data = {"India", "USA", "UK"};
  ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, data);
  adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
  spinner.setAdapter(adapter);
  ```

---

## 5. Pickers
### DatePicker
```java
Calendar cal = Calendar.getInstance();
new DatePickerDialog(this, (view, y, m, d) -> {
    String date = d + "/" + (m+1) + "/" + y;
}, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show();
```

---

## 6. Data Storage (SQLite)
### Step 1: Create DatabaseHelper Class
```java
public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context) { super(context, "UserDB", null, 1); }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE users (id INTEGER PRIMARY KEY, name TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int old, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        onCreate(db);
    }
    
    public void insert(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        db.insert("users", null, cv);
    }
}
```

---

## 7. Fragments (How to Create & Use)
### Step 1: Create Fragment XML
Create `res/layout/fragment_test.xml` with your desired UI.

### Step 2: Create Fragment Class
```java
public class TestFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test, container, false);
    }
}
```

### Step 3: Use Fragment in Activity
1. **Layout**: Add a container in your Activity XML:
   ```xml
   <FrameLayout android:id="@+id/fragmentContainer" android:layout_width="match_parent" android:layout_height="match_parent" />
   ```
2. **Java**: Load the fragment:
   ```java
   getSupportFragmentManager().beginTransaction()
       .replace(R.id.fragmentContainer, new TestFragment())
       .commit();
   ```

---

## 8. TableLayout (Dynamic Rows)
To show SQL data in a table:
```java
TableLayout table = findViewById(R.id.tableLayout);
Cursor c = db.getAllData();
while(c.moveToNext()) {
    TableRow row = new TableRow(this);
    TextView tv = new TextView(this);
    tv.setText(c.getString(1)); // Column 1 data
    row.addView(tv);
    table.addView(row);
}
```

---
💡 **Tip**: Always check `Logcat` for errors and ensure IDs in Java match your XML!
