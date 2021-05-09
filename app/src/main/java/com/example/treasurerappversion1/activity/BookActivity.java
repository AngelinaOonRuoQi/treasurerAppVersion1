package com.example.treasurerappversion1.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.treasurerappversion1.R;

import java.util.ArrayList;

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
    Button btnAdd, btnStudent;
    TableLayout table;
    TextView tvTextTitle;

    double sum = 0;
    int currentItemIndex = 0;


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
        tvTextTitle = findViewById(R.id.textTittle);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();
            }
        });
        btnStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookActivity.this, StudentActivity.class);
                intent.putExtra("semester", tvTextTitle.getText());
                startActivity(intent);
            }
        });

        table = (TableLayout) findViewById(R.id.tb1);

        if (getIntent().getStringExtra("sem_title") != null) {
            tvTextTitle.setText(getIntent().getStringExtra("sem_title"));
        }


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
        String course = "";
        double price = 0.00;
        double qty = 0.00;
        if (!editCourse.getText().toString().isEmpty()) {
            course = editCourse.getText().toString();
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

        data.add(course);
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
}