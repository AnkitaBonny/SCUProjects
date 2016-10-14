package com.example.computer1.myarttherapy;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager mSensorManager;
    private long SensorTime;
    private long SensorForce;
    private int ShakeIterator = 0;
    private long SensorShake;
    private float Accelerator;
    private static final int SHAKE_THRESHOLD = 50;
    private static final int SHAKE_SLOP_DURATION = 2000;
    private static final int SENSOR_THRESHOLD = 350;
    private static final int SHAKE_COUNT = 3;
    private static final int SHAKE_TIMEOUT = 750;
    private float SensorX = -1.0f, SensorY = -1.0f, SensorZ = -1.0f;
    private DrawView customDraw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        customDraw = (DrawView) findViewById(R.id.draw);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        SensorForce = System.currentTimeMillis();
        SensorTime = SensorForce;
        SensorShake = SensorForce;

    }

    public void clearCanvas(View v) {
        customDraw.clearCanvas();

    }

    protected void OnResume() {
        super.onResume();
        mSensorManager.registerListener(this,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(this);
        super.onPause();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        long when = System.currentTimeMillis();
        if ((when - SensorForce) > SHAKE_TIMEOUT) {
            ShakeIterator = 0;
        }

        if ((when - SensorTime) > SENSOR_THRESHOLD) {
            long diff = when - SensorTime;
            Accelerator = Math.abs(event.values[0] + event.values[1] + event.values[2] - SensorX - SensorY - SensorZ) / diff * 10000;
            if (Accelerator > SHAKE_THRESHOLD) {
                if ((++ShakeIterator >= SHAKE_COUNT) && ((when - SensorShake) > SHAKE_SLOP_DURATION)) {
                    Intent intent = new Intent(this,Eraser.class);
                    startService(intent);
                    clearCanvas(customDraw);
                    SensorShake = when;
                    ShakeIterator = 0;
                }
                SensorForce = when;
            }
            else
            {

            }

            SensorTime = when;
            SensorX = event.values[0];
            SensorY = event.values[1];
            SensorZ = event.values[2];
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
