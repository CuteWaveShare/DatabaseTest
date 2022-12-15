package com.example.administrator.databasetest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.example.administrator.ProviderTest.Main2Activity;
import com.waveshare.databasetest.R;

public class MainActivity extends AppCompatActivity{
    MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        dbHelper = new MyDatabaseHelper(this, "BookStore.db", null, 1);
        dbHelper.getWritableDatabase();
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
    }
}