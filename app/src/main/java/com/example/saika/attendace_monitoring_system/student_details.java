package com.example.saika.attendace_monitoring_system;

import android.content.ClipData;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.saika.attendace_monitoring_system.MainActivity.adrress;

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
                HttpPost httpPost = new HttpPost(adrress+"/fetch_students.php");
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                result = EntityUtils.toString(httpEntity);

            } catch (Exception e) {
                result = e.toString();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            String result = s.toString();

            JSONArray jsonArray = null;
            try {
                jsonArray = new JSONArray(result);
            }
            catch (JSONException e) {
                e.printStackTrace();
            }

            //String[] a=null;
           // String[] b=null;
            final List< String > names = new ArrayList< String >();
            List< String > roll = new ArrayList< String >();
            final List<String>id=new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {

                try {
                    JSONObject   x = jsonArray.getJSONObject(i);

                    String s_id = x.getString("id");
                    String name = x.getString("name");
                    String roll_no = x.getString("roll");
                    id.add(s_id);
                    names.add(name);
                    roll.add(roll_no);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                simpleAdapter=new SimpleAdapter(student_details.this,names,roll);
                lv1.setAdapter(simpleAdapter);
                lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String a= names.get(i);
                        String ids=id.get(i);
                       // Toast.makeText(student_details.this,a,Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(student_details.this,details.class);
                        intent.putExtra("item",ids);
                        startActivity(intent);
                    }
                });
            }

        }

    }
}



