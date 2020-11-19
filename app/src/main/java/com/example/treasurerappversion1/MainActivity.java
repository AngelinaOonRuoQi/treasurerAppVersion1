package com.example.treasurerappversion1;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.treasurerappversion1.model.Semester;

import java.util.ArrayList;

import static android.view.MotionEvent.ACTION_DOWN;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvSemester;
    SemesterAdapter2 semesterAdapter;
    ArrayList<Semester> semesterList = new ArrayList<>();
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        semesterList.add(new Semester(1, "Y1S1"));
        semesterList.add(new Semester(2, "Y1S2"));
        semesterList.add(new Semester(3, "Y1S3"));

        Log.w("sz",semesterList.get(0).getName());

        rvSemester = findViewById(R.id.semesterRv);
        layoutManager = new LinearLayoutManager(this);
        rvSemester.setLayoutManager(layoutManager);
        semesterAdapter = new SemesterAdapter2(getApplicationContext(), semesterList);

        semesterAdapter.setOnItemClickListener(new ListItemClickListener() {
            @Override
            public void onListItemClick(int position) {
                Toast.makeText(MainActivity.this,"position is "+position,Toast.LENGTH_SHORT).show();
            }
        });
        rvSemester.setAdapter(semesterAdapter);

    }
}
