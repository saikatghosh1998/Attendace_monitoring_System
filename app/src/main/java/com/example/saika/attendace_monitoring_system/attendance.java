package com.example.saika.attendace_monitoring_system;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class attendance extends AppCompatActivity {
Button timein,timeout;
int a=1;
int b=2;
SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        timein= findViewById(R.id.time_in);
        timeout= findViewById(R.id.time_out);

        sharedPreferences = getSharedPreferences("MyPREFERENCES",MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        timein.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putInt("time",a);
                editor.commit();
                Intent intent=new Intent(attendance.this,mark.class);
                startActivity(intent);
            }
        });
        timeout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putInt("time",b);
                editor.commit();
                Intent intent=new Intent(attendance.this,mark.class);
                startActivity(intent);
            }
        });
    }
}
