package com.example.a2lazy2do;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.a2lazy2do.BE.Task;
import com.example.a2lazy2do.BE.User;
import com.example.a2lazy2do.holders.TaskListViewHolder;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static com.example.a2lazy2do.R.id.action_signOut;

public class MainActivity extends AppCompatActivity {

    String userEmail, userName;
    FirebaseAuth auth;
    FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
    CollectionReference userTaskRef;
    FirestoreRecyclerAdapter<Task, TaskListViewHolder> firestoreRecyclerAdapter;
    Button load;
    private Context context;

    private static final int RC_SIGN_IN = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;


        auth = FirebaseAuth.getInstance();
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());

        if (auth.getCurrentUser() != null) {
            //user already signed in
            Log.d("AUTH", auth.getCurrentUser().getEmail());

            userEmail = auth.getCurrentUser().getEmail();
            userName = auth.getCurrentUser().getDisplayName();

        } else {
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .build(),
                    RC_SIGN_IN);
        }


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Create a new Task");

                final EditText editText = new EditText(MainActivity.this);
                editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
                editText.setHint("Type a task to do");
                editText.setHintTextColor(Color.GRAY);
                builder.setView(editText);

                builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String taskName = editText.getText().toString().trim();
                        addTasks(taskName);
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        load = findViewById(R.id.Load);
        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load.setVisibility(View.GONE);
                onStart();
            }
        });
    }


    @Override
    protected void onStart(){
        super.onStart();

        if(load.getVisibility() == View.GONE) {
            startView();
            firestoreRecyclerAdapter.startListening();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        if(firestoreRecyclerAdapter != null){
            firestoreRecyclerAdapter.stopListening();
        }
    }

    private void addTasks(String taskName){
        String taskId = userTaskRef.document().getId();
        Task task = new Task(taskId, taskName, userName);
        userTaskRef.document(taskId).set(task).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("TAG" , "task sucessfully created");
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            if(resultCode == RESULT_OK){
                Log.d("AUTH", auth.getCurrentUser().getEmail());

                userEmail = auth.getCurrentUser().getEmail();
                userName = auth.getCurrentUser().getDisplayName();

                String userEmail = auth.getCurrentUser().getEmail();
                String userName = auth.getCurrentUser().getDisplayName();
                String tokenId = FirebaseInstanceId.getInstance().getToken();

                User user = new User(userEmail,userName,tokenId);
                rootRef.collection("users").document(userEmail).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("User", "user successfully created");
                    }
                });

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
        Map<String, Object> map = new HashMap<>();
        map.put("tokenId" , FieldValue.delete());
        rootRef.collection("users").document(userEmail).update(map)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Log.d("TOKEN" , "TOKEN NOT DELETED");
                    }
                }).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                    Log.d("TOKEN" , "TOKEN DELETED SCORNFULLY");
                    }
                });

        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(com.google.android.gms.tasks.Task<Void> task) {
                        Log.d("AUTH", "USER LOGGED OUT");
                        finish();
                    }
                });
    }

    private void startView(){
    userTaskRef = rootRef.collection("Tasks").document(userEmail).collection("userTasks");

    final RecyclerView recyclerView = findViewById(R.id.listview);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    final TextView emptyView = findViewById(R.id.empty_view);
    final ProgressBar progressBar = findViewById(R.id.progress_bar);
    final SearchView screach = findViewById(R.id.search);

    Query query = userTaskRef.orderBy("taskName", Query.Direction.ASCENDING);
    FirestoreRecyclerOptions<Task> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Task>().setQuery(query, Task.class).build();

    firestoreRecyclerAdapter =
            new FirestoreRecyclerAdapter<Task, TaskListViewHolder>(firestoreRecyclerOptions) {
        @Override
        protected void onBindViewHolder(TaskListViewHolder holder, int position, Task model) {
            holder.setTaskList(context, userEmail,  model);
        }

        @Override
        public TaskListViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listview_item, viewGroup, false);
            return new TaskListViewHolder(view);
        }

        @Override
        public void onDataChanged() {
            if(progressBar != null){
                progressBar.setVisibility(View.GONE);
            }
            if(getItemCount() == 0){
                recyclerView.setVisibility(View.GONE);
                screach.setVisibility(View.GONE);
                emptyView.setVisibility(View.VISIBLE);
            }else{
                recyclerView.setVisibility(View.VISIBLE);
                screach.setVisibility(View.VISIBLE);
                emptyView.setVisibility(View.GONE);
            }
        }

        @Override
        public int getItemCount() {
            return super.getItemCount();
        }
    };
        recyclerView.setAdapter(firestoreRecyclerAdapter);
    }

}
