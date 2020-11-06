package com.example.treasurerappversion1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> data =new ArrayList<>();
    private ArrayList<String> data1 =new ArrayList<>();
    private ArrayList<String> data2 =new ArrayList<>();
    private ArrayList<String> data3 =new ArrayList<>();

    EditText editCourse,editQty,editPrice,editSubTotal;
    Button btnAdd,btnStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        editCourse=(EditText) findViewById(R.id.editCourse);
        editQty=(EditText)findViewById(R.id.editQty);
        editPrice=(EditText)findViewById(R.id.editPrice);
        editSubTotal=(EditText)findViewById(R.id.editSubTotal);
        btnAdd=(Button) findViewById(R.id.buttonAdd);
        btnStudent=(Button) findViewById(R.id.buttonStudent);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add();
            }
        });
        btnStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,StudentActivity.class);
                startActivity(intent);
            }
        });

    }

    public void add(){
        int tot;
        String course=editCourse.getText().toString();
        int price=Integer.parseInt(editPrice.getText().toString());
        int qty=Integer.parseInt(editQty.getText().toString());
        tot=price*qty;

        data.add(course);
        data1.add(String.valueOf(qty));
        data2.add(String.valueOf(price));
        data3.add(String.valueOf(tot));

        TableLayout table=(TableLayout) findViewById(R.id.tb1);
        TableRow row=new TableRow(this);
        TextView row1= new TextView(this);
        TextView row2= new TextView(this);
        TextView row3= new TextView(this);
        TextView row4= new TextView(this);

        String total;
        int sum=0;
        for(int i =0;i<data.size();i++){
            String cours = data.get(i);
            String qtyy = data1.get(i);
            String pri = data2.get(i);
            total = data3.get(i);

            row1.setText(cours);
            row2.setText(qtyy);
            row3.setText(pri);
            row4.setText(total);

            sum=sum+Integer.parseInt(data3.get(i).toString());
        }
        row.addView(row1);
        row.addView(row2);
        row.addView(row3);
        row.addView(row4);
        table.addView(row);

        editSubTotal.setText(String.valueOf(sum));
        editCourse.setText("");
        editQty.setText("");
        editPrice.setText("");
        editCourse.requestFocus();
    }
}