package com.example.treasurerappversion1.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.treasurerappversion1.R;
import com.example.treasurerappversion1.model.Student;

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {
    private ArrayList<Student> mStudentList;

    private static ClickListener clickListener;


    public static class StudentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        public TextView name, paidStatus;
        public CardView cardView;

        public StudentViewHolder(final View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.student_name);
            paidStatus = itemView.findViewById(R.id.student_paid);
            cardView = itemView.findViewById(R.id.student_card);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }

        @Override
        public boolean onLongClick(View v) {
            clickListener.onItemLongClick(getAdapterPosition(), v);
            return false;
        }


    }

    public StudentAdapter(ArrayList<Student> studentList) {
        mStudentList = studentList;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_cart_items_layout, parent, false);
        StudentViewHolder studentViewHolder = new StudentViewHolder(view);
        return studentViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student current = mStudentList.get(position);
        holder.name.setText(current.getName());
        holder.paidStatus.setText(current.getPaidStatus());
    }

    @Override
    public int getItemCount() {
        return mStudentList.size();
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        StudentAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);

        void onItemLongClick(int position, View v);
    }
}
