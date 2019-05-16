package com.example.a2lazy2do.fragments;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.a2lazy2do.BE.Product;
import com.example.a2lazy2do.BE.Task;
import com.example.a2lazy2do.MainActivity;
import com.example.a2lazy2do.R;
import com.example.a2lazy2do.TaskListActivity;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class taskListFragment extends Fragment {
    private String taskId;
    private FirebaseFirestore rootRef;
    private CollectionReference taskProductRef;
    private boolean izCompleted;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View taskListFragment = inflater.inflate(R.layout.fragment_tasklist, container, false);
        Bundle bundle = getArguments();
        izCompleted = bundle.getBoolean("izCompleted");

        Task task = ((TaskListActivity) getActivity()).getTask();
        taskId = task.getTaskId();

        FloatingActionButton fab = taskListFragment.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Create a new Sub-Task");

                final EditText editText = new EditText(getContext());
                editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
                editText.setHint("Type a task to do");
                editText.setHintTextColor(Color.GRAY);
                builder.setView(editText);

                builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String productName = editText.getText().toString().trim();
                        addProduct(productName);
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

        rootRef = FirebaseFirestore.getInstance();
        taskProductRef = rootRef.collection("products").document(taskId).collection("taskProduct");

        return taskListFragment;
    }

    private void addProduct(String productName){
        String productId = taskProductRef.document().getId();
        Product product = new Product(productId, productName, izCompleted);
        taskProductRef.document(productId).set(product);
    }
}
