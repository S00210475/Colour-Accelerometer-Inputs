package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    Button btnUp, btnDown, btnLeft, btnRight;
    Vibrator v;
    boolean positionChange, atBase;
    private SensorManager mSensorManager;
    private Sensor mSensor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //For testing
        Log.i("Test", "Test 1");
        btnUp = findViewById(R.id.btnUp);
        btnDown = findViewById(R.id.btnDown);
        btnRight = findViewById(R.id.btnRight);
        btnLeft = findViewById(R.id.btnLeft);

        // Get instance of Vibrator from current Context
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // choose the sensor you want
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }
    /*
     * When the app is brought to the foreground - using app on screen
     */
    protected void onResume() {
        super.onResume();
        // turn on the sensor
        mSensorManager.registerListener(this, mSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
    }
    /*
     * App running but not on screen - in the background
     */
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);    // turn off listener to save power
    }
    /*
     * Called by the system every x millisecs when sensor changes
     */
    public void onSensorChanged(SensorEvent event) {
        // called byt the system every x ms
        Log.i("Test", "Test 2");
        float x, y, z;
        x = Math.abs(event.values[0]); // get x value
        y = event.values[1];
        z = Math.abs(event.values[2]);
        if(y < -2)
        {
            colorClick(btnLeft, atBase);
            positionChange = true;
        }
        else if(y > 2)
        {
            colorClick(btnRight, atBase);
            positionChange = true;
        }
        else if(x>9)
        {
            colorClick(btnDown, atBase);
            positionChange = true;
        }
        else if(x<4)
        {
            colorClick(btnUp, atBase);
            positionChange = true;
        }
        else{
            positionChange = false;
            atBase = true;
        }
        if(positionChange && atBase)
        {
            v.vibrate(100);
            positionChange = false;
            atBase = false;
        }
    }
    public void colorClick(Button btn, boolean click)
    {
        if(click) {
            btn.performClick();
            btn.setPressed(true);
            btn.invalidate();
            btn.setPressed(false);
            btn.invalidate();
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // not using
    }
}