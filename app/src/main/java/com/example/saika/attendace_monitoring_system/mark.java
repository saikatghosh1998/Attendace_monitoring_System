package com.example.saika.attendace_monitoring_system;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission_group.CAMERA;

public class mark extends AppCompatActivity  implements ZXingScannerView.ResultHandler{
    SharedPreferences sharedPreferences;
    login.LoginTask loginTask;
    TextView tv,tv2;
    private static final int REQUEST_CAMERA=1;
    private ZXingScannerView ScannerView;


    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_mark);
        ScannerView=new ZXingScannerView(this);
        setContentView(ScannerView);
       //tv=(TextView)findViewById(R.id.tv1);
       //tv2=(TextView)findViewById(R.id.tv2);


       // sharedPreferences = getSharedPreferences("MyPREFERENCES",MODE_PRIVATE);
        //tv.setText(login.sharedPreferences.getString("id",null));
       // tv2.setText(login.sharedPreferences.getString("email",null));


        if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.M)
        {
            if(checkPermission())
            {
                Toast.makeText(mark.this,"Permission granted", Toast.LENGTH_LONG).show();
            }
            else
            {
                requestPermission();
            }
        }
    }

    private boolean checkPermission()
    {
        return (ContextCompat.checkSelfPermission(mark.this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED);

    }
    private void requestPermission()
    {
        ActivityCompat.requestPermissions(this,new String[]{CAMERA},REQUEST_CAMERA);
    }

    public  void onRequestPermissionResult(int requestCode,String Permission[],int grantResults[])
    {
        switch (requestCode){
            case REQUEST_CAMERA:
                if(grantResults.length>0)
                {
                    boolean cameraAccepted=grantResults[0]==PackageManager.PERMISSION_GRANTED;
                    if(cameraAccepted)
                    {
                        Toast.makeText(mark.this,"Permission Granted",Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(mark.this,"Permission Denied",Toast.LENGTH_LONG).show();
                        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.M)
                        {
                            if(shouldShowRequestPermissionRationale(CAMERA))
                            {
                                displayAlertMessage("you need to grant permissions", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                            requestPermissions(new String[]{CAMERA},REQUEST_CAMERA);
                                        }
                                    }
                                });
                                return;
                            }
                        }
                    }
                }
                break;
        }
    }
    @Override
    public void onResume()
    {
        super.onResume();
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
        {
            if(checkPermission())
            {
                if(ScannerView==null)
                {
                    ScannerView=new ZXingScannerView(this);
                    setContentView(ScannerView);
                }
                ScannerView.setResultHandler(this);
                ScannerView.startCamera();
            }
            else {
                requestPermission();
            }
        }
    }
    @Override
    public  void onDestroy() {
        super.onDestroy();
        ScannerView.stopCamera();
    }
    public void displayAlertMessage(String message, DialogInterface.OnClickListener listener)
    {
        new AlertDialog.Builder(mark.this)
                .setMessage(message)
                .setPositiveButton("ok",listener)
                .setNegativeButton("cancel",null)
                .create()
                .show();
    }
    @Override
    public void handleResult(final Result result) {
        sharedPreferences = getSharedPreferences("MyPREFERENCES",MODE_PRIVATE);
        //sharedPreferences = getSharedPreferences("times",MODE_PRIVATE);
        final String scanResult=result.getText();
        final String id=sharedPreferences.getString("id",null);
        final String email=sharedPreferences.getString("email",null);
        final int timev=sharedPreferences.getInt("time",0);


        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Scam Result");
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               // submit_class submit_class=new submit_class(timev,scanResult,id,email);
                ScannerView.resumeCameraPreview(mark.this);
                if(timev==1) {
                    Intent intent = new Intent(mark.this, submit_attendance.class);
                    //intent.putExtra("timev",timev);
                    intent.putExtra("scanResult", scanResult);
                    intent.putExtra("id", id);
                    intent.putExtra("email", email);
                    startActivity(intent);
                }else{
                    Intent intent=new Intent(mark.this,time_out.class);
                    intent.putExtra("scanResult", scanResult);
                    intent.putExtra("id", id);
                    intent.putExtra("email", email);
                    startActivity(intent);
                }
            }
        });
        builder.setNeutralButton("Visit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(scanResult));
                startActivity(intent);
            }
        });
        builder.setMessage(scanResult+" "+" "+id);
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }
}
