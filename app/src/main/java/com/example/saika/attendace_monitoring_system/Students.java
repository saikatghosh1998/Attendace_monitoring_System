
package com.example.saika.attendace_monitoring_system;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.saika.attendace_monitoring_system.MainActivity.adrress;

public class Students extends AppCompatActivity {

EditText sname,roll,sphone,email,dob,adress,course;
Button  AddStudent;
    ProgressDialog progressDialog;

static TextView errorview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);
        sname= findViewById(R.id.sname_id);
        roll= findViewById(R.id.sroll_id);
       sphone= findViewById(R.id.sphone_id);
        email= findViewById(R.id.semail_id);
        dob= findViewById(R.id.sdob_id);
        adress= findViewById(R.id.sadress_id);
        course= findViewById(R.id.scourse_id);
        AddStudent= findViewById(R.id.AddStudents_id);
        errorview= findViewById(R.id.error_id);
        AddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String NAME=sname.getText().toString();
                String ROLL=roll.getText().toString();
                String PHONE=sphone.getText().toString();
                String EMAIL=email.getText().toString();
                String DOB=dob.getText().toString();
                String ADRESS=adress.getText().toString();
                String COURSE=course.getText().toString();

                new MyServerTask().execute(ROLL , NAME ,PHONE , EMAIL , DOB , ADRESS , COURSE);
                }
        });
    }
    static class MyServerTask extends AsyncTask<String,Void, String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           // progressDialog=new ProgressDialog(Students.this);
          //  progressDialog.setMessage("please wait");
           // progressDialog.setCancelable(false);


        }

        @Override
        protected String doInBackground(String... params) {

            String RES=null;
            String result=null;
            String ROLL=params[0];
            String NAME=params[1];
            String PHONE=params[2];
            String EMAIL=params[3];
            String DOB=params[4];
            String ADRESS=params[5];
            String COURSE=params[6];
            try
            {
                HttpClient httpClient=new DefaultHttpClient();
                HttpPost httpPost=new HttpPost(adrress+"/login1.php");

                ArrayList<NameValuePair> DataList=new ArrayList<>();
                DataList.add(new BasicNameValuePair("roll" ,ROLL));
                DataList.add(new BasicNameValuePair("name" ,NAME));
                DataList.add(new BasicNameValuePair("phone" ,PHONE));
                DataList.add(new BasicNameValuePair("email" , EMAIL));
                DataList.add(new BasicNameValuePair("dob" , DOB));
                DataList.add(new BasicNameValuePair("adress" ,ADRESS));
                DataList.add(new BasicNameValuePair("course" , COURSE));
                DataList.add(new BasicNameValuePair("password" , PHONE));
                httpPost.setEntity(new UrlEncodedFormEntity(DataList));
                HttpResponse httpResponse= httpClient.execute(httpPost);
                HttpEntity httpEntity= httpResponse.getEntity();
                RES= EntityUtils.toString(httpEntity);

               // JSONArray jsonArray = new JSONArray(result);

              /*  for (int i = 0; i < jsonArray.length(); i++) {

                   JSONObject x=jsonArray.getJSONObject(i);
                   String name=x.getString("name");
                   String phone= x.getString("phone");
                   String email=x.getString("email");
                    RES+= "Name : "+name+"\n"+"Phone : "+phone+"\n"+"Email : "+email+"\n";
                }*/

            }
            catch (Exception e)
            {
                RES=e.toString();
            }
            return RES;
        }

        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);
            //progressDialog.dismiss();
            errorview.setText(s);
          

        }
    }

}


