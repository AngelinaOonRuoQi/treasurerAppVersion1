package com.example.treasurerappversion1.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.treasurerappversion1.ListItemClickListener;
import com.example.treasurerappversion1.R;
import com.example.treasurerappversion1.adapters.SemesterAdapter2;
import com.example.treasurerappversion1.model.Semester;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvSemester;
    SemesterAdapter2 semesterAdapter;
    ArrayList<Semester> semesterList = new ArrayList<>();
    RecyclerView.LayoutManager layoutManager;
    EditText etSemesterName;

    Button btnPlus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        semesterList.add(new Semester(1, "Y1S1"));
        semesterList.add(new Semester(2, "Y1S2"));
        semesterList.add(new Semester(3, "Y1S3"));

        Log.w("sz", semesterList.get(0).getName());

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
                    semesterList.add(new Semester(semesterAdapter.getItemCount() + 1, etSemesterName.getText().toString()));
                    semesterAdapter.notifyDataSetChanged();
                    etSemesterName.setText("");
                } else {
                    etSemesterName.requestFocus();
                    etSemesterName.setError("Please key in a value");
                }

            }
        });

    }
}
