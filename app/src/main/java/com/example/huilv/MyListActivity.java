package com.example.huilv;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class MyListActivity extends ListActivity{
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_my_list);

        List<String> list = new ArrayList<String>();
        for (int i = 1; i < 100; i++) {
            list.add("item" + i);
        }
        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        setListAdapter(adapter);

        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == 9) {
                    List<String> list2 = (List<String>) msg.obj;
                    ListAdapter adapter = new ArrayAdapter<String>(MyListActivity.this, android.R.layout.simple_list_item_1, list2);
                    setListAdapter(adapter);
                    Toast.makeText(MyListActivity.this, "ret size=" + list.size(), Toast.LENGTH_SHORT).show();
                }
                super.handleMessage(msg);
            }
        };
        MyTask task = new MyTask();
        task.setHandler(handler);
        Thread thread = new Thread(task);
        thread.start();

    }
}