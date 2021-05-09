package com.example.treasurerappversion1.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.treasurerappversion1.R;
import com.example.treasurerappversion1.model.Course;
import com.example.treasurerappversion1.model.Student;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//TODO: To Fix
//TODO: A) what is sub total? Isn't that Grand Total?
//To modify later
//TODO: B) When press save, need to show popup validate?
//Will do when function is added
//TODO: C) Is supposed to be double, not int when reading price
//Fix already


//TODO: 1) Teach on Validating EditText -show editTextOption and textWatcher
//Teached
//addTextChangedListener - noticed there is listener pattern
//TODO: 2) Guide Layout, teach about ScrollView and Dynamic Layouting
//Teached
//TODO: 3) Introduce shortcutkey ctrl+alt+l to auto format the codes
//Teached
//TODO: 4) Introduce Input Filter, don't teach because is a bit advance
//Example: editCourse.setFilters(new InputFilter[]{new InputFilter.AllCaps()});


//TODO: 5) Introduce the most basic Object Oriented, don't teach too much because is very advance
//Done Introducing 4 basic OOP Concept
//TODO: 6) Help to write Firebase, because it can be a little tricky to understand how other Firebase database works.

//TODO: Show that add() function don't need to use for loop

//TODO: Guide to use git merge
//Done.

//TODO: Give her to practice merge conflict
//

public class BookActivity extends AppCompatActivity {
    private ArrayList<String> data = new ArrayList<>();
    private ArrayList<String> data1 = new ArrayList<>();
    private ArrayList<String> data2 = new ArrayList<>();
    private ArrayList<String> data3 = new ArrayList<>();

    EditText editCourse, editQty, editPrice, editSubTotal;
    Button btnAdd, btnSave, btnStudent;
    TableLayout table;
    TextView tvTextTitle;

    double sum = 0;
    int currentItemIndex = 0;

    DatabaseReference currentSemesterCoursesRef;
    FirebaseDatabase database;

    String sem_id = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        //Comment

        editCourse = (EditText) findViewById(R.id.editCourse);
        editQty = (EditText) findViewById(R.id.editQty);
        editPrice = (EditText) findViewById(R.id.editPrice);
        editSubTotal = (EditText) findViewById(R.id.editSubTotal);
        btnAdd = (Button) findViewById(R.id.buttonAdd);
        btnStudent = (Button) findViewById(R.id.buttonStudent);
        btnSave = (Button) findViewById(R.id.buttonSave);
        tvTextTitle = findViewById(R.id.textTittle);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });




        table = (TableLayout) findViewById(R.id.tb1);

        if (getIntent().getStringExtra("sem_title") != null) {
            tvTextTitle.setText(getIntent().getStringExtra("sem_title"));
        }

        if (getIntent().getStringExtra("sem_id") != null) {
            sem_id = getIntent().getStringExtra("sem_id");
        }

        btnStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookActivity.this, StudentActivity.class);
                intent.putExtra("semester", tvTextTitle.getText());
                intent.putExtra("sem_id", sem_id);
                startActivity(intent);
            }
        });


        database = FirebaseDatabase.getInstance();
        currentSemesterCoursesRef = database.getReference("Semesters/" + sem_id + "/ListOfCourses");

        initializeCurrentSemesterCourses();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void add() {
        String courseName = "";
        double price = 0.00;
        double qty = 0.00;
        if (!editCourse.getText().toString().isEmpty()) {
            courseName = editCourse.getText().toString();
        } else {
            showErrorMessage("Please key in Course Details");
            return;
        }

        if (!editPrice.getText().toString().isEmpty()) {
            price = Double.parseDouble(editPrice.getText().toString());
        } else {
            showErrorMessage("Please key in Price");
            return;
        }

        if (!editQty.getText().toString().isEmpty()) {
            qty = Integer.parseInt(editQty.getText().toString());
        } else {
            showErrorMessage("Please key in Quantity");
            return;
        }
        double tot = price * qty;
        sum = sum + price * qty;

        data.add(courseName);
        data1.add(String.valueOf(qty));
        data2.add(String.valueOf(price));
        data3.add(String.valueOf(tot));

        TextView row1 = new TextView(this);
        TextView row2 = new TextView(this);
        TextView row3 = new TextView(this);
        TextView row4 = new TextView(this);

        row1.setText(data.get(currentItemIndex));
        row2.setText(data1.get(currentItemIndex));
        row3.setText(data2.get(currentItemIndex));
        row4.setText(data3.get(currentItemIndex));

        //Create new row
        TableRow row = new TableRow(this);

        //Popuplate the row
        row.addView(row1);
        row.addView(row2);
        row.addView(row3);
        row.addView(row4);

        //Make changes by calling table to add the row that was just populated
        table.addView(row);


        //reset editText
        editSubTotal.setText(String.valueOf(sum));
        editCourse.setText("");
        editQty.setText("");
        editPrice.setText("");
        editCourse.requestFocus();

        currentItemIndex++;


    }

    private void showErrorMessage(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    private void initializeCurrentSemesterCourses() {

        currentSemesterCoursesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                table.removeAllViews();

                for (DataSnapshot single :
                        snapshot.getChildren()) {
                    Course course = single.getValue(Course.class);

                    TextView row1 = new TextView(BookActivity.this);
                    TextView row2 = new TextView(BookActivity.this);
                    TextView row3 = new TextView(BookActivity.this);
                    TextView row4 = new TextView(BookActivity.this);

                    row1.setText(course.getName());
                    row2.setText(course.getQuantity() + "");
                    row3.setText(course.getPrice() + "");
                    row4.setText((course.getPrice() * course.getQuantity()) + "");

                    //Create new row
                    TableRow row = new TableRow(BookActivity.this);

                    //Popuplate the row
                    row.addView(row1);
                    row.addView(row2);
                    row.addView(row3);
                    row.addView(row4);

                    //Make changes by calling table to add the row that was just populated
                    table.addView(row);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void save() {
        for (int i = 0; i < data.size(); i++) {
            Map<String, Object> courseHashMap = new HashMap<>();

            Course courseTemp = new Course();
            courseTemp.setId((currentItemIndex + 1) + "");
            courseTemp.setName(data.get(i));
            courseTemp.setPrice(Double.valueOf(data1.get(i)));
            courseTemp.setQuantity(Double.valueOf(data2.get(i)));

            courseHashMap.put((i+1)+"", courseTemp);
            currentSemesterCoursesRef.setValue(courseHashMap);
        }
        editCourse.setText("");
        editQty.setText("");
        editPrice.setText("");
        editSubTotal.setText("");

        closeKeyboard();

        Toast.makeText(this, "Data Saved", Toast.LENGTH_SHORT).show();

    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}