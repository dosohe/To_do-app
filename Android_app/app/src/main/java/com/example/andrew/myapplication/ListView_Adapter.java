package com.example.andrew.myapplication;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.TreeSet;

import static com.example.andrew.myapplication.MainActivity.todos;

class ListView_Adapter extends BaseAdapter {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_SEPARATOR = 1;

    private ArrayList<String> mData = new ArrayList<>();
    private TreeSet<Integer> sectionHeader = new TreeSet<>();

    private LayoutInflater mInflater;

    public ListView_Adapter(Context context) {
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addItem(final String item) {
        mData.add(item);
        notifyDataSetChanged();
    }

    public void addSectionHeaderItem(final String item) {
        mData.add(item);
        sectionHeader.add(mData.size() - 1);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return sectionHeader.contains(position) ? TYPE_SEPARATOR : TYPE_ITEM;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public String getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        int rowType = getItemViewType(position);

        if (convertView == null) {
            holder = new ViewHolder();
            switch (rowType) {
                case TYPE_ITEM:
                    convertView = mInflater.inflate(R.layout.cell, null);
                    holder.checkBox = convertView.findViewById(R.id.checkBox);
                    break;
                case TYPE_SEPARATOR:
                    convertView = mInflater.inflate(R.layout.header, null);
                    holder.textView =  convertView.findViewById(R.id.textSeparator);
                    break;
            }
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (holder.textView != null) holder.textView.setText(mData.get(position));
        for (int i = 0; i < todos.size(); i++) {
            MainActivity.Todo todo = todos.get(i);
            if (mData.get(position).equals(todo.text)) {
                holder.checkBox.setText(todo.text);
                holder.checkBox.setId(i);
                //to define if the checked state of a compound button changed
                holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        MainActivity.Todo todoIsCompl = todos.get(buttonView.getId());

                        if ( todoIsCompl.isCompleted == isChecked) return;
                        MainActivity.update(todoIsCompl.todo_id);
                        if ( todoIsCompl.isCompleted) {
                            todoIsCompl.isCompleted = false;
                            buttonView.setPaintFlags(buttonView.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
                        }
                        else {
                            todoIsCompl.isCompleted = true;
                            buttonView.setPaintFlags(buttonView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        }

                    }
                });
                holder.checkBox.setChecked(todo.isCompleted);
                if (holder.checkBox.isChecked()) holder.checkBox.setPaintFlags(holder.checkBox.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                else holder.checkBox.setPaintFlags(holder.checkBox.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
            }
        }

        return convertView;
    }

    public static class ViewHolder {
        public TextView textView;
        public CheckBox checkBox;
    }

}