package com.example.treasurerappversion1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.treasurerappversion1.model.Semester;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=findViewById(R.id.semesterList);

        final ArrayList<Semester> semesterList = new ArrayList<>();
        semesterList.add(new Semester(1,"Y1S1"));
        semesterList.add(new Semester(22,"Y1S2"));
        semesterList.add(new Semester(33,"Y1S3"));


        SemesterAdapter adapter = new SemesterAdapter(this,semesterList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(MainActivity.this, "Hi " + semesterList.get(position).getId(), Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MainActivity.this, BookActivity.class);
                startActivity(i);
            }


        });
    }



}
