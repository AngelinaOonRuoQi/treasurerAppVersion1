package com.example.treasurerappversion1.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.treasurerappversion1.ListItemClickListener;
import com.example.treasurerappversion1.R;
import com.example.treasurerappversion1.adapters.SemesterAdapter2;
import com.example.treasurerappversion1.model.Course;
import com.example.treasurerappversion1.model.Semester;
import com.example.treasurerappversion1.model.Student;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvSemester;
    SemesterAdapter2 semesterAdapter;
    ArrayList<Semester> semesterList = new ArrayList<>();
    RecyclerView.LayoutManager layoutManager;
    EditText etSemesterName;

    Button btnPlus;

    FirebaseDatabase database;
    DatabaseReference semestersRef;

    int semesterListSize = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Write a message to the database
        database = FirebaseDatabase.getInstance();
        semestersRef = database.getReference("Semesters/");


        rvSemester = findViewById(R.id.rv_semester);
        etSemesterName = findViewById(R.id.et_semester_name);
        btnPlus = findViewById(R.id.btn_plus);
        layoutManager = new LinearLayoutManager(this);
        rvSemester.setLayoutManager(layoutManager);
        semesterAdapter = new SemesterAdapter2(getApplicationContext(), semesterList);

        semesterAdapter.setOnItemClickListener(new ListItemClickListener() {
            @Override
            public void onListItemClick(int position) {
                Intent i = new Intent(MainActivity.this, BookActivity.class);
                i.putExtra("sem_title", semesterList.get(position).getName());
                startActivity(i);

            }
        });


        rvSemester.setAdapter(semesterAdapter);

        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etSemesterName.getText().toString().isEmpty()) {
//                    semesterList.add(new Semester(semesterAdapter.getItemCount() + 1, etSemesterName.getText().toString()));

                    List<Student> emptyStudent = new ArrayList<>();
//                    Student student = new Student();
//                    student.setName("empty");
//                    student.setPaidStatus("unpaid");
//                    emptyStudent.add(student);

                    List<Course> emptyCourse = new ArrayList<>();
//                    Course course = new Course();
//                    course.setId("1");
//                    course.setName("empty");
//                    course.setPrice("empty");
//                    course.setQty("empty");
//                    emptyCourse.add(course);

                    Semester semesterTemp = new Semester();
                    semesterTemp.setId(semesterListSize+1);
                    semesterTemp.setName(etSemesterName.getText().toString());


                    semestersRef.child(etSemesterName.getText().toString()).setValue(semesterTemp);
//                    Map<String, Object> updates = new HashMap<>();
//                    updates.put(etSemesterName.getText().toString()+"/listOfStudents", "empty");
//                    updates.put(etSemesterName.getText().toString()+"/listOfCourses", "empty");
//
//                    semestersRef.updateChildren(updates);
                    semesterList.add(semesterTemp);
                    semesterAdapter.notifyDataSetChanged();
                    etSemesterName.setText("");
                } else {
                    etSemesterName.requestFocus();
                    etSemesterName.setError("Please key in a value");
                }

            }
        });

        initializeCurrentSemesterList();

    }

    private void initializeCurrentSemesterList() {

        semestersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

//                Toast.makeText(MainActivity.this, snapshot.getValue().toString(), Toast.LENGTH_SHORT).show();

                if (!semesterList.isEmpty())
                    semesterList.clear();

                for (DataSnapshot single :
                        snapshot.getChildren()) {
                    Semester semester = new Semester();
                    semester.setName(single.child("name").getValue().toString());
                    semester.setId(Integer.valueOf(single.child("id").getValue().toString()));

                    semesterList.add(semester);
                    semesterAdapter.notifyDataSetChanged();

                }

                semesterListSize = semesterList.size();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

}
