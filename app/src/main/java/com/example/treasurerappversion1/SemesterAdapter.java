package com.example.treasurerappversion1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.treasurerappversion1.model.Semester;

import java.util.ArrayList;

public class SemesterAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Semester> semester;

    public SemesterAdapter(Context context, ArrayList<Semester> semester) {
        this.context = context;
        this.semester = semester;
    }

    @Override
    public int getCount() {
        return semester.size();
    }

    @Override
    public Object getItem(int position) {
        return semester.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       if(convertView==null){
           convertView= LayoutInflater.from(context).inflate(R.layout.semerter_main_list,parent,false);

       }
       Semester currentSemester = (Semester) getItem(position);
        TextView textViewItemId = convertView.findViewById(R.id.semester_item_id);
        TextView textViewItemName = convertView.findViewById(R.id.semester_item_name);

        textViewItemId.setText(Integer.toString(currentSemester.getId()));
        textViewItemName.setText(currentSemester.getName());
       return convertView;
    }
}
