package com.example.andrew.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;

import java.util.ArrayList;

public class ProjectAdapter extends BaseAdapter {
    ArrayList<String> names;
    Context context;
    LayoutInflater inflter;


    public ProjectAdapter(Context context, ArrayList<String> names) {
        this.context = context;
        this.names = names;
        inflter = (LayoutInflater.from(context));

    }

    @Override
    public int getCount() {
        return names.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        view = inflter.inflate(R.layout.projects_choise, null);
        final CheckedTextView simpleCheckedTextView = view.findViewById(R.id.TextView);
        simpleCheckedTextView.setText(names.get(position));
        simpleCheckedTextView.setId(position + 1);


        simpleCheckedTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (simpleCheckedTextView.isChecked()) {
                    simpleCheckedTextView.setChecked(false);
                    TodoCreate.checkedTextView = null;
                } else {
                    if (TodoCreate.checkedTextView != null && TodoCreate.checkedTextView.isChecked()) TodoCreate.checkedTextView.setChecked(false);
                    simpleCheckedTextView.setChecked(true);
                    TodoCreate.checkedTextView = simpleCheckedTextView;
                }
            }
        });
        return view;
    }
}
