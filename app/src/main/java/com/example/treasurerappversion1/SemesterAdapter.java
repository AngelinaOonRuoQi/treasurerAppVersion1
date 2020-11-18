package com.example.treasurerappversion1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.treasurerappversion1.model.Semester;

import java.util.ArrayList;

public class SemesterAdapter extends RecyclerView.Adapter<SemesterAdapter.ViewHolder> {
    private static final String Tag = "RecyclerView";
    private Context mContext;
    private ArrayList<Semester> semesterList;
    private ListItemClickListener mOnClickListener;


    public SemesterAdapter(Context mContext, ArrayList<Semester> semesterList) {
        this.mContext = mContext;
        this.semesterList = semesterList;
    }

    @NonNull
    @Override
    public SemesterAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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
            getOnItemClickListener().onListItemClick(getAdapterPosition());
        }
    }

    public ListItemClickListener getOnItemClickListener() {
        return mOnClickListener;
    }

    public void setOnItemClickListener(ListItemClickListener onItemClickListener) {
        this.mOnClickListener = onItemClickListener;
    }
}

