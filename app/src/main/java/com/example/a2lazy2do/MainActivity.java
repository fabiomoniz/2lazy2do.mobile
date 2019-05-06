package com.example.a2lazy2do;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;
import com.example.a2lazy2do.BE.Task;
import java.util.ArrayList;

import static com.example.a2lazy2do.R.id.action_newList;

public class MainActivity extends AppCompatActivity {

    Toolbar mToolbar;
    ListView mListView;

    ArrayList<Task> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("2Lazy2Do");
        mToolbar.inflateMenu(R.menu.main_menu) ;

        mListView = (ListView) findViewById(R.id.listview);

        tasks = new ArrayList<>();

        Task task = new Task("Do something" , "something" , null );
        tasks.add(task) ;

        MyAdapter myAdapter = new MyAdapter(MainActivity.this , tasks);
        mListView.setAdapter(myAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu) ;

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menu) {
        switch (menu.getItemId()) {
            case action_newList:
                clickSignIn();
                return true;
                default:
                    return super.onOptionsItemSelected(menu);

        }
    }

    public void clickSignIn(){
        Intent x = new Intent(this, SignInActivity.class);
        startActivity(x);
    }

}
