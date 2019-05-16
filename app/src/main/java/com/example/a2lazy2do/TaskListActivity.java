package com.example.a2lazy2do;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.a2lazy2do.BE.Task;
import com.example.a2lazy2do.fragments.taskListFragment;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TaskListActivity extends AppCompatActivity {

    String userEmail;
    FirebaseAuth auth;
    FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
    private Task task;
    private String taskId;
    EditText editText;
    private static final int RC_SIGN_IN = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        auth = FirebaseAuth.getInstance();
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());

        if (auth.getCurrentUser() != null) {
            //user already signed in
            userEmail = auth.getCurrentUser().getEmail();

        } else {
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .build(),
                    RC_SIGN_IN);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        task = (Task) getIntent().getSerializableExtra("Task");
        String taskName = task.getTaskName();
        setTitle(taskName);
        taskId = task.getTaskId();

        ViewPager viewPager = findViewById(R.id.view_pager);
        setupViewPager(viewPager);
        viewPager.setOffscreenPageLimit(1);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;

            case R.id.action_add_friend:
                shareTask();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void shareTask() {
        AlertDialog.Builder builder = new AlertDialog.Builder(TaskListActivity.this);
        builder.setTitle("Share a Task");
        builder.setMessage("Please insert your friends email");

        editText = new EditText(TaskListActivity.this);
        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        editText.setHint("Type an Email");
        editText.setHintTextColor(Color.GRAY);
        builder.setView(editText);

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String friendEmail = editText.getText().toString().trim().toLowerCase();
                rootRef.collection("Tasks").document(friendEmail)
                        .collection("userTasks").document(taskId)
                        .set(task);
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

    private class ViewPagerAdapter extends FragmentPagerAdapter{
        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> titleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return fragmentList.get(i);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titleList.get(position);
        }

        void addFragment(Fragment fragment, String title){
            fragmentList.add(fragment);
            titleList.add(title);
        }
    }

    public Task getTask() { return task;}

    private void setupViewPager( ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        taskListFragment trueFragment = new taskListFragment();
        Bundle trueBundle = new Bundle();
        trueBundle.putBoolean("izCompleted" , true);
        trueFragment.setArguments(trueBundle);
        viewPagerAdapter.addFragment(trueFragment, "TASK LIST");

        taskListFragment falseFragment = new taskListFragment();
        Bundle falseBundle = new Bundle();
        falseBundle.putBoolean("izCompleted" , false);
        falseFragment.setArguments(falseBundle);
        viewPagerAdapter.addFragment(falseFragment, "DONE");

        viewPager.setAdapter(viewPagerAdapter);
    }
}
