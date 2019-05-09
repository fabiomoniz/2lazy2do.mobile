package com.example.a2lazy2do;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;
import com.example.a2lazy2do.BE.Task;
import com.example.a2lazy2do.DataAccess.IDataAccess;

import java.util.ArrayList;

import static com.example.a2lazy2do.R.id.action_newList;
import static com.example.a2lazy2do.R.id.action_signIn;

public class MainActivity extends AppCompatActivity {

    Toolbar mToolbar;
    ListView mListView;
    IDataAccess db;
    ArrayList<Task> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.listview);

        tasks = new ArrayList<>();
        Task task = new Task("Do something" , "something" , null ); // delete later
        tasks.add(task) ;

        MyAdapter myAdapter = new MyAdapter(MainActivity.this , tasks);
        mListView.setAdapter(myAdapter);

        Task task2 = new Task("Do something else" , "something" , null ); //delete later
        tasks.add(task2) ;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case action_signIn:
                clickSignIn();
                return true;
            case action_newList:
                addCheckList();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onListItemClick(ListView parent, View v, int position,
                                long id) {
        Intent x = new Intent(this, MyAdapter.class);
        Task task = tasks.get(position);
        x.putExtra("task", task);
        startActivity(x);
    }

    public void clickSignIn(){
        Intent x = new Intent(this, SignInActivity.class);
        startActivity(x);
    }

    public void addCheckList(){
        Intent x = new Intent(this, CheckList.class);
        startActivity(x);
    }

}
