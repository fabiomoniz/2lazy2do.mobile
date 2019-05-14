package com.example.a2lazy2do;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import com.example.a2lazy2do.BE.Task;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.a2lazy2do.R.id.action_signOut;

public class MainActivity extends AppCompatActivity {


    ListView mListView;
    ArrayList<Task> tasks;
    FirebaseAuth auth;
    private static final int RC_SIGN_IN = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());

        if(auth.getCurrentUser() != null){
            //user already signed in
            Log.d("AUTH", auth.getCurrentUser().getEmail());
        }else{
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .build(),
                    RC_SIGN_IN);
        }



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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            if(requestCode == RESULT_OK){
                Log.d("AUTH", auth.getCurrentUser().getEmail());
            }else {
                //user not authenticated
                Log.d("AUTH", "NOT AUTHENTICATED");
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case action_signOut:
                clickSignOut();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void clickSignOut(){
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {
                        Log.d("AUTH", "USER LOGGED OUT");
                        finish();
                    }
                });
    }

}
