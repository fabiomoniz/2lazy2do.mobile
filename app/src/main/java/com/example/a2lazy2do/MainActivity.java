package com.example.a2lazy2do;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;

import com.example.a2lazy2do.BE.Task;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Toolbar mToolbar;
    ListView mListView;

    ArrayList<Task> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mListView = (ListView) findViewById(R.id.listview);

        tasks = new ArrayList<>();
        mToolbar.setTitle("2Lazy2Do");

        MyAdapter myAdapter = new MyAdapter(MainActivity.this , tasks);
        mListView.setAdapter(myAdapter);
    }
}
