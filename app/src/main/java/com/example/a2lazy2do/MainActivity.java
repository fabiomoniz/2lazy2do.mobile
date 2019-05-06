package com.example.a2lazy2do;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

        Task task = new Task("something" , "something" , null );
        tasks.add(task) ;

        mToolbar.inflateMenu(R.menu.main_menu) ;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu) ;

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menu) {
        switch (menu.getItemId()) {
            case R.id.action_newList:
                return true;
                default:
                    return super.onOptionsItemSelected(menu) ;
        }
    }

}
