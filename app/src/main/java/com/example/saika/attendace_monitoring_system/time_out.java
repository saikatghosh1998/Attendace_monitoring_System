package com.example.saika.attendace_monitoring_system;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;

import static com.example.saika.attendace_monitoring_system.MainActivity.adrress;
import static com.example.saika.attendace_monitoring_system.submit_attendance.lastid;

public class time_out extends AppCompatActivity {
static TextView tv;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_out);
        tv= findViewById(R.id.timeout_id);
        Bundle extras = getIntent().getExtras();
        String time = extras.getString("scanResult");
        String id = extras.getString("id");
        String a=lastid;
        new timeout().execute( id,time,a);

    }
    static class timeout extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {


            String RES = null;
            String s_id = params[0];
            String time_out = params[1];
            String last_id=params[2];
            String status="A";


            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(adrress+"/time_out.php");
                //HttpPost httpPost = new HttpPost("http://192.168.0.103:7099/time_in.php");
                ArrayList<NameValuePair> DataList = new ArrayList<>();
                DataList.add(new BasicNameValuePair("s_id", s_id));
                DataList.add(new BasicNameValuePair("time_out", time_out));
                DataList.add(new BasicNameValuePair("id",last_id));
                DataList.add(new BasicNameValuePair("status", status));
                httpPost.setEntity(new UrlEncodedFormEntity(DataList));
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                RES = EntityUtils.toString(httpEntity);


            } catch (Exception e) {
                RES = e.toString();
            }

            return RES;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            tv.setText(s);

        }
    }


}


