package com.example.saika.attendace_monitoring_system;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class student_attendance extends AppCompatActivity {
Button details,attendance;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_attendance);
        details= findViewById(R.id.Details);
        attendance= findViewById(R.id.Attendance);
        sharedPreferences = getSharedPreferences("MyPREFERENCES",MODE_PRIVATE);
        final String id=sharedPreferences.getString("id",null);
        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(student_attendance.this,details.class);
                intent.putExtra("item",id);
                startActivity(intent);
            }
        });
        attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(student_attendance.this,attendance.class);
                startActivity(intent);
            }
        });
    }
}
