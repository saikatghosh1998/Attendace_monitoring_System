package com.example.saika.attendace_monitoring_system;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class login extends AppCompatActivity {
EditText userid,password;
Button login;
static TextView lerrorview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userid=(EditText)findViewById(R.id.luser);
        password=(EditText)findViewById(R.id.lpass);
        login=(Button)findViewById(R.id.login);
        lerrorview=(TextView)findViewById(R.id.lerror_id);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email=userid.getText().toString();
                String pass=password.getText().toString();
              new LoginTask().execute(email,pass);
            }
        });

    }
public class authenticate{
        String a,b;
        public void set(String x,String y){
            a=x;
            b=y;

        }
    }


    static class LoginTask extends AsyncTask<String, Void, String[]> {

        @Override
        protected String[] doInBackground(String... params) {

            String RES=null;
            String result=null;
            String email=null;
            String pass=null;
            String id=null;
            String Email=params[0];
            String password=params[1];
            try
            {
                HttpClient httpClient=new DefaultHttpClient();
                HttpPost httpPost=new HttpPost("http://192.168.0.103:7099/newlogin.php");
                ArrayList<NameValuePair> DataList=new ArrayList<>();

                DataList.add(new BasicNameValuePair("email" , Email));
                DataList.add(new BasicNameValuePair("password" , password));
                httpPost.setEntity(new UrlEncodedFormEntity(DataList));
                HttpResponse httpResponse= httpClient.execute(httpPost);
                HttpEntity httpEntity= httpResponse.getEntity();
                result= EntityUtils.toString(httpEntity);

                JSONArray jsonArray = new JSONArray(result);


                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject x = jsonArray.getJSONObject(i);
                        email = x.getString("email");
                        pass = x.getString("password");
                        id=x.getString("id");
                        RES += "Email : " + email + "\n" + "password : " + pass + "\n";
                       // RES = new String[]{"Logged in succesfully"};



                }
            }
            catch (Exception e)
            {
                RES=e.toString();
            }
            //return RES;
           return new String[] { email, pass ,id};
        }

        @Override
        protected void onPostExecute(String[] s)
        {
            super.onPostExecute(s);
            //progressDialog.dismiss();
            lerrorview.setText(s[0]+"\n"+s[1]+"\n"+s[2]);

        }
    }

}
