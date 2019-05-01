package com.example.a2lazy2do;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.a2lazy2do.BE.Task;

import java.util.ArrayList;

public class MyAdapter extends ArrayAdapter<String> {

    ArrayList<Task> tasks;
    Context mContext;

    public MyAdapter(Context context, ArrayList<Task> tasks ) {
        super(context, R.layout.listview_item);
        this.tasks = tasks;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return tasks.size();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder = new ViewHolder();
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) mContext.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.listview_item, parent, false);
            mViewHolder.mImage = (ImageView) convertView.findViewById(R.id.imageView);
            mViewHolder.mText = (TextView) convertView.findViewById(R.id.textView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        //mViewHolder.mImage.setImageResource(tasks.getImage[position]); // setting the image if there is one (if statement needed)
        //mViewHolder.mText.setText(tasks.getText[position]);  //set text / subject

        return convertView;
    }

    static class ViewHolder {
        ImageView mImage;
        TextView mText;
    }
}