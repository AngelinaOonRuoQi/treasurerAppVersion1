package com.example.treasurerappversion1.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.treasurerappversion1.ListItemClickListener;
import com.example.treasurerappversion1.R;
import com.example.treasurerappversion1.model.Semester;

import java.util.ArrayList;

public class SemesterAdapter2 extends RecyclerView.Adapter<SemesterAdapter2.ViewHolder> {
    private static final String Tag = "RecyclerView";
    private Context mContext;
    private ArrayList<Semester> semesterList;
    private ListItemClickListener mOnClickListener;


    public SemesterAdapter2(Context mContext, ArrayList<Semester> semesterList) {
        this.mContext = mContext;
        this.semesterList = semesterList;
    }

    @NonNull
    @Override
    public SemesterAdapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.semester_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvName.setText(semesterList.get(position).getName());
        holder.tvId.setText(String.valueOf(semesterList.get(position).getId()));

    }

    @Override
    public int getItemCount() {
        return semesterList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tvId, tvName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.semester_item_name);
            tvId = (TextView) itemView.findViewById(R.id.semester_item_id);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnClickListener.onListItemClick(getAdapterPosition());
        }
    }



    public void setOnItemClickListener(ListItemClickListener onItemClickListener) {
        this.mOnClickListener = onItemClickListener;
    }
}

