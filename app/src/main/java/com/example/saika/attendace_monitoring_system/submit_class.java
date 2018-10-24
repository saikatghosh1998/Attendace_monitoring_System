package com.example.saika.attendace_monitoring_system;

import android.content.SharedPreferences;

public class submit_class {
    SharedPreferences sharedPreferences;

    String time,email,id,timev;
     submit_class(String timev,String time, String id,String email){
         this.time=time;
         this.id=id;
         this.email=email;
         this.timev=timev;
     }




}
