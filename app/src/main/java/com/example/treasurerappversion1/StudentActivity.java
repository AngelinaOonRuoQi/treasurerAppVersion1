package com.example.treasurerappversion1;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StudentActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        final ArrayList<StudentName> studentName=new ArrayList<>();
        studentName.add(new StudentName("Angelina Oon"));
        studentName.add(new StudentName("Chong Mei Xue"));
        studentName.add(new StudentName("Koh Ke Xin"));

        mRecyclerView=findViewById(R.id.recyclerViewName);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager= new LinearLayoutManager(this);
        mAdapter= new StudentAdapter(studentName);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

    }


}
