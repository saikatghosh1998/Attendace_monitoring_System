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
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.saika.attendace_monitoring_system.MainActivity.adrress;

public class details extends AppCompatActivity {
static TextView tvname,tvphone,tvemail,tvcourse,tvroll;
    CircleImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        tvname= findViewById(R.id.nameview_id);
        tvphone= findViewById(R.id.phoneview_id);
        tvemail= findViewById(R.id.emailview_id);
        tvcourse= findViewById(R.id.courseview_id);
        tvroll= findViewById(R.id.rollview_id);
        imageView= findViewById(R.id.circularimage_id);
        imageView.setImageResource(R.drawable.student);

        String id=getIntent().getExtras().getString("item");
        //tv.setText("NAME:   "+name);

        new studentDetail().execute(id);
    }

    static class studentDetail extends AsyncTask<String, Void, String[]> {

        @Override
        protected String[] doInBackground(String... params) {


            String RES=null;
            String result=null;
            String id=params[0];
            String email=null;
            String name=null;
            String phone=null;
            String roll=null;
            String course=null;

            try
            {
                HttpClient httpClient=new DefaultHttpClient();
                HttpPost httpPost=new HttpPost(adrress+"/details.php");
                ArrayList<NameValuePair> DataList=new ArrayList<>();
                DataList.add(new BasicNameValuePair("id" , id));
                httpPost.setEntity(new UrlEncodedFormEntity(DataList));
                HttpResponse httpResponse= httpClient.execute(httpPost);
                HttpEntity httpEntity= httpResponse.getEntity();
                result= EntityUtils.toString(httpEntity);

                JSONArray jsonArray = new JSONArray(result);
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject x = jsonArray.getJSONObject(i);
                    id=x.getString("id");
                    name=x.getString("name");
                    email = x.getString("email");
                    phone = x.getString("phone");
                    roll=x.getString("roll");
                    course=x.getString("course");
                    //RES=id+" "+name+" "+email+" "+phone+" "+roll+" "+course;
                }
            }
            catch (Exception e)
            {
                result=e.toString();
            }
            return new String[] { id,name,roll,email,phone,course};

        }

        @Override
        protected void onPostExecute(String[] s)
        {
            super.onPostExecute(s);
            tvname.setText("Name : "+s[1]);
            tvroll.setText("Roll No.: "+s[2]);
            tvemail.setText("Email ID : "+s[3]);
            tvphone.setText("Phone No.: "+s[4]);
            tvcourse.setText("Course : "+s[5]);
            //lerrorview.setText(s[0]+"\n"+s[1]+"\n"+s[2]);

        }
    }

}

