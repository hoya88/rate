package com.example.huilv;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class MyRateListActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_rate_list);
        ListView listView = findViewById(R.id.mylist);

    }

    @Override
    public boolean onSupportNavigateUp() {
        return false;
    }
}