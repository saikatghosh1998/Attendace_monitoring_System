package com.example.saika.attendace_monitoring_system;

import android.content.SharedPreferences;
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
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.saika.attendace_monitoring_system.MainActivity.adrress;
import static com.example.saika.attendace_monitoring_system.submit_attendance.tv;

public class submit_attendance extends AppCompatActivity {
    static TextView tv;
    static  String time=null;
    static String lastid;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_attendance);

        tv = findViewById(R.id.tvat);
        Bundle extras = getIntent().getExtras();
        //String timev = extras.getString("timev");
        time = extras.getString("scanResult");
        String id = extras.getString("id");

        new time_in().execute(time, id);

    }


    static class time_in extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {

            String result=null;
            String  RES ;
            String time_in = params[0];
            String s_id = params[1];


            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(adrress + "/time_in.php");
                ArrayList<NameValuePair> DataList = new ArrayList<>();
                DataList.add(new BasicNameValuePair("time_in", time_in));
                DataList.add(new BasicNameValuePair("s_id", s_id));
                httpPost.setEntity(new UrlEncodedFormEntity(DataList));
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                result = EntityUtils.toString(httpEntity);

                JSONArray jsonArray = new JSONArray(result);
                   RES = jsonArray.getString(0);

            } catch (Exception e) {
                RES = e.toString();
            }

            return RES;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            lastid=s;
            tv.setText(s);

        }
    }


}