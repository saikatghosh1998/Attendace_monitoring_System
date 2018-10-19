package com.example.saika.attendace_monitoring_system;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class student_details extends AppCompatActivity {
    static TextView tv1;
    static ListView lv1;
    static SimpleAdapter simpleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);
        lv1= findViewById(R.id.lv_id);
        new StudentDetails().execute();

    }

    class StudentDetails extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            String RES = "";
            String result = null;

            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://192.168.0.103:7099/fetch_students.php");


                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                result = EntityUtils.toString(httpEntity);

             /*JSONArray jsonArray = new JSONArray(result);

                for (int i = 0; i < jsonArray.length(); i++) {

                   JSONObject x=jsonArray.getJSONObject(i);
                   String name=x.getString("name");
                   String phone= x.getString("phone");
                   String email=x.getString("email");
                    RES+= "Name : "+name+"\n"+"Phone : "+phone+"\n"+"Email : "+email+"\n\n";
                   // RES+=name;
                }
                */

            } catch (Exception e) {
                result = e.toString();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //progressDialog.dismiss();
            //tv1.setText(s);
            String result = s.toString();

            JSONArray jsonArray = null;
            try {
                jsonArray = new JSONArray(result);
            }
            catch (JSONException e) {
                e.printStackTrace();
            }

            String[] a=null;
            String[] b=null;
            List< String > names = new ArrayList< String >();
            List< String > roll = new ArrayList< String >();
            for (int i = 0; i < jsonArray.length(); i++) {

                try {
                    JSONObject   x = jsonArray.getJSONObject(i);

                    String name = x.getString("name");
                    //String phone = x.getString("phone");
                    String roll_no = x.getString("roll");
                    names.add(name);
                    roll.add(roll_no);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                simpleAdapter=new SimpleAdapter(student_details.this,names,roll);
                lv1.setAdapter(simpleAdapter);
            }

        }

    }
}



