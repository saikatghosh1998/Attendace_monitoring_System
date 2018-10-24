package com.example.saika.attendace_monitoring_system;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class admin extends AppCompatActivity {
    Button register,details;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);


        register= findViewById(R.id.students_id);
        details= findViewById(R.id.studentsDetails_id);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(admin.this,Students.class);
                startActivity(intent);
            }
        });
        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(admin.this,student_details.class);
                startActivity(intent);
            }
        });
    }
}
