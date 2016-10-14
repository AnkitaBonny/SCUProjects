package com.example.computer1.birddirectory;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class InformationActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback{
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    String myData = "tel:5551234";
    Intent myIntent = new Intent(Intent.ACTION_CALL, Uri.parse(myData));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        TextView name = (TextView)findViewById(R.id.textView);
        name.setText("Bird Kingdom!!" );

        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int permissionCheck = ContextCompat.checkSelfPermission(InformationActivity.this,
                        Manifest.permission.CALL_PHONE);
                if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(InformationActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CODE_ASK_PERMISSIONS);

                }
                else {

                    startActivity(myIntent);

                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults){
        if(requestCode==REQUEST_CODE_ASK_PERMISSIONS){
            startActivity(myIntent);
        }
        else {
            Toast.makeText(InformationActivity.this, "Permission not granted", Toast.LENGTH_SHORT).show();
        }
        return;
    }


}
