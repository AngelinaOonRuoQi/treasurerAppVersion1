package com.example.treasurerappversion1.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.treasurerappversion1.R;
import com.example.treasurerappversion1.model.Student;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {
    private ArrayList<Student> mStudentList;

    public static class StudentViewHolder extends RecyclerView.ViewHolder{

        public TextView name;
        public CardView cardView;

        public StudentViewHolder(final View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.student_name);
            cardView=itemView.findViewById(R.id.student_card);

            itemView.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onClick(View view) {

                        itemView.setBackgroundColor(R.color.colorPrimary);

                }

            });
        }
    }

    public StudentAdapter(ArrayList<Student> studentList){
        mStudentList=studentList;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_cart_items_layout,parent,false);
        StudentViewHolder studentViewHolder=new StudentViewHolder(view);
        return studentViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student current=mStudentList.get(position);
        holder.name.setText(current.getName());
    }

    @Override
    public int getItemCount() {
        return mStudentList.size();
    }
}
