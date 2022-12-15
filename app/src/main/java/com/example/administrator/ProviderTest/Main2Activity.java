package com.example.administrator.ProviderTest;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.waveshare.databasetest.R;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {
    private String TAG = "-----------------Main2Activity-----------------";
    private String newID;
    private Button btnInsert;
    private Button btnDelete;
    private Button btnUpdate;
    private Button btnSelect;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnInsert = findViewById(R.id.insert);
        btnDelete = findViewById(R.id.delete);
        btnUpdate = findViewById(R.id.update);
        btnSelect = findViewById(R.id.select);
        textView = findViewById(R.id.resText);
        btnInsert.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnSelect.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.insert:
                ContentValues values = new ContentValues();
                Uri uri = Uri.parse("content://com.example.administrator."
                        + "databasetest.DatabaseProvider/book");
                values.put("name", "A Clash of kings");
                values.put("author", "George Martin");
                values.put("pages", 1040);
                values.put("price", 22.85);
                Uri newUri = getContentResolver().insert(uri, values);
                newID = newUri.getPathSegments().get(1);
                myQuery();
                break;
            case R.id.delete:
                Uri uri1 = Uri.parse("content://com.example.administrator."
                        + "databasetest.DatabaseProvider/book");
                getContentResolver().delete(uri1, null, null);
                Log.d(TAG, "delete " + newID);
                myQuery();
                break;
            case R.id.update:
                ContentValues values1 = new ContentValues();
                Uri uri2 = Uri.parse("content://com.example.administrator."
                        + "databasetest.DatabaseProvider/book");
                values1.put("name", "A Storm of Swords");
                values1.put("pages", 1216);
                values1.put("price", 24.05);
                getContentResolver().update(uri2, values1, null, null);
                Log.d(TAG, "update " + newID);
                myQuery();
                break;
            case R.id.select:
                myQuery();
                break;

        }
    }

    public void myQuery() {
        String res = "";
        Uri uri3 = Uri.parse("content://com.example.administrator."
                + "databasetest.DatabaseProvider/book");
        Cursor cursor = getContentResolver().query(uri3, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                @SuppressLint("Range") String author = cursor.getString(cursor.getColumnIndex("author"));
                @SuppressLint("Range") int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                @SuppressLint("Range") Double price = cursor.getDouble(cursor.getColumnIndex("price"));
                res += "book name is:" + name + "\n" +
                       "book author is:" + author + "\n" +
                       "book pages is:" + pages + "\n" +
                       "book price is:" + price + "\n";
            }
            textView.setText(res);
            cursor.close();
        }
    }
}