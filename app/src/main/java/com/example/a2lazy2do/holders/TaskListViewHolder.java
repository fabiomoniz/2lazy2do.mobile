package com.example.a2lazy2do.holders;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import com.example.a2lazy2do.BE.Task;
import com.example.a2lazy2do.R;
import com.example.a2lazy2do.TaskListActivity;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class TaskListViewHolder extends RecyclerView.ViewHolder {

    private TextView taskName, createdBy, date;


    public TaskListViewHolder(View itemView) {
        super(itemView);
        taskName =  (TextView) itemView.findViewById(R.id.taskTitle);
        createdBy = itemView.findViewById(R.id.createdBy);
        date = itemView.findViewById(R.id.date);
    }

    public void setTaskList(final Context context, final String userEmail, final Task task){ //dont know why task has to be final ask teacher
        final String taskId = task.getTaskId();
        final String taskTitle = task.getTaskName();
        taskName.setText(taskTitle);

        String createdByString = "Created by " + task.getCreatedBy();
        createdBy.setText(createdByString);

        Date dateString = task.getTimestamp();
        if (dateString != null){
            DateFormat dateFormat = SimpleDateFormat.getDateInstance(DateFormat.MEDIUM, Locale.US);
            String creationDate = dateFormat.format(dateString);
            date.setText(creationDate);
        }

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TaskListActivity.class);
                intent.putExtra("Task" , task);
                v.getContext().startActivity(intent);
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Edit Tasks name");

                final EditText editText = new EditText(context);
                editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
                editText.setText(taskTitle);
                editText.setSelection(editText.getText().length());
                editText.setHint("Type a name");
                editText.setHintTextColor(Color.GRAY);
                builder.setView(editText);

                final FirebaseFirestore rootref = FirebaseFirestore.getInstance();
                final Map<String, Object> map = new HashMap<>();

                builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newTaskName = editText.getText().toString().trim();
                        map.put("taskName", newTaskName);
                        rootref.collection("Tasks").document(userEmail).collection("userTasks").document(taskId).update(map);
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

                return true;
            }
        });
    }
}
