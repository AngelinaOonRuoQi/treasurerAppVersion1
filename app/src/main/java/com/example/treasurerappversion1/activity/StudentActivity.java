package com.example.treasurerappversion1.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.treasurerappversion1.R;
import com.example.treasurerappversion1.adapters.StudentAdapter;
import com.example.treasurerappversion1.model.Student;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



public class StudentActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private StudentAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    DatabaseReference studentRef;
    DatabaseReference semesterRef;

    DatabaseReference currentSemesterRef;
    FirebaseDatabase database;
    TextView semesterTitleTv;

    int studentCount = 0;
    int studentAddedCount = 0;

    CharSequence loadItems[];
    ArrayList<String> studentNames = new ArrayList();
    ArrayList<String> currentStudentList = new ArrayList();
    ArrayList<Student> currentSemstudent = new ArrayList<>();

    String sem_id = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        semesterTitleTv = findViewById(R.id.tv_student_semester_title);

        Intent i = getIntent();
        if (i != null) {
            semesterTitleTv.setText(i.getStringExtra("semester"));
            sem_id = i.getStringExtra("sem_id");
        }

        // Write a message to the database
        database = FirebaseDatabase.getInstance();
        studentRef = database.getReference("Students");
        semesterRef = database.getReference("Semesters");

        currentSemesterRef = database.getReference("Semesters/" + sem_id + "/ListOfStudents");

        mRecyclerView = findViewById(R.id.recyclerViewName);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(StudentActivity.this);

        mRecyclerView.setLayoutManager(mLayoutManager);


        mAdapter = new StudentAdapter(currentSemstudent);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new StudentAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {


                Map<String, Object> studentUpdates = new HashMap<>();
                studentUpdates.put("paidStatus", currentSemstudent.get(position).getPaidStatus().equals("unpaid") ? "paid" : "unpaid");

                currentSemesterRef.child(String.valueOf(position + 1)).updateChildren(studentUpdates);


            }

            @Override
            public void onItemLongClick(int position, View v) {

            }
        });

        initializeStudentRefListener();
        initializeCurrentSemesterStudents();


    }

    private void initializeStudentRefListener() {
        studentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                studentCount = (int) snapshot.getChildrenCount();
                studentNames = new ArrayList<>();
                for (DataSnapshot single :
                        snapshot.getChildren()) {
                    studentNames.add(single.getValue().toString());

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void initializeCurrentSemesterStudents() {

        currentSemesterRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                studentAddedCount = (int) snapshot.getChildrenCount();



                if (!currentSemstudent.isEmpty())
                    currentSemstudent.clear();

                if (!currentStudentList.isEmpty())
                    currentStudentList.clear();

                for (DataSnapshot single :
                        snapshot.getChildren()) {
                    Student student = single.getValue(Student.class);

                    currentStudentList.add(student.getName());
//
                    currentSemstudent.add(student);
                    mAdapter.notifyDataSetChanged();


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void addStudent(String name) {
        studentRef.child(studentCount + 1 + "").setValue(name);
    }

    private void showAddNewStudentsPopup() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Add the buttons
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked Save button
                EditText editCourse = ((AlertDialog) dialog).findViewById(R.id.new_student_name_et);
                addStudent(editCourse.getText().toString());
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        builder.setView(R.layout.new_student_popup);
        // Set other dialog properties
        // Create the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showStudentListPopup() {


        currentSemesterRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> newList = new ArrayList<>();
                for (DataSnapshot studentAdded : snapshot.getChildren()) {
                    Log.w("value", "valstudentNamesue is " + studentAdded.getValue());
                    for (int i = 0; i < studentNames.size(); i++) {
                        if (studentNames.contains(studentAdded.getValue().toString())) {
                            studentNames.remove(studentAdded.getValue().toString());
                        }
                    }
                }


                final CharSequence loadItems[] = studentNames.toArray(new CharSequence[studentNames.size()]);

                final ArrayList<String> selectedItems = new ArrayList();  // Where we track the selected items
                AlertDialog.Builder builder = new AlertDialog.Builder(StudentActivity.this);
                // Set the dialog title
                builder.setTitle("Add Student Into Semester")
                        // Specify the list array, the items to be selected by default (null for none),
                        // and the listener through which to receive callbacks when items are selected
                        .setMultiChoiceItems(loadItems, null,
                                new DialogInterface.OnMultiChoiceClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which,
                                                        boolean isChecked) {
                                        if (isChecked) {
                                            // If the user checked the item, add it to the selected items
                                            selectedItems.add(loadItems[which].toString());
                                        } else if (selectedItems.contains(which)) {
                                            // Else, if the item is already in the array, remove it
                                            selectedItems.remove(loadItems[which].toString());
                                        }

                                    }
                                })
                        // Set the action buttons
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                // User clicked OK, so save the selectedItems results somewhere
                                // or return them to the component that opened the dialog
                                for (String item : selectedItems) {
//                                    currentSemesterRef.child(studentAddedCount + 1 + "").setValue(item);
                                    Map<String, Student> studentHashMap = new HashMap<>();
                                    Student student = new Student();
                                    student.setName(item);
                                    student.setPaidStatus("unpaid");
                                    studentHashMap.put(studentAddedCount + 1 + "", student);
                                    currentSemesterRef.child(studentAddedCount + 1 + "").setValue(student);
                                    studentAddedCount++;


                                }
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });

                // Set other dialog properties
                // Create the AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_students:
                showAddNewStudentsPopup();
                return true;
            case R.id.add_student:
                showStudentListPopup();
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
