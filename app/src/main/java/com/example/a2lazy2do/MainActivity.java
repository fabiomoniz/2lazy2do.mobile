package com.example.a2lazy2do;

import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;

import com.example.a2lazy2do.BE.Task;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView mListView;
    Toolbar mToolbar;
    ArrayList<Task> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mListView = (ListView) findViewById(R.id.listview);

        tasks = new ArrayList<>();
        Task task = new Task("something" , "something" , null );
        tasks.add(task);

        MyAdapter myAdapter = new MyAdapter(MainActivity.this , tasks);
        mListView.setAdapter(myAdapter);
        mToolbar.inflateMenu(R.menu.main_menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menu) {
        switch (menu.getItemId()) {
            case R.id.action_newList:
                return true;
                default:
                    return super.onOptionsItemSelected(menu);
        }
    }

}

