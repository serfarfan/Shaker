package com.sergio.shake.View;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.sergio.shake.Controller.MainController;
import com.sergio.shake.R;

public class MainActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private SeekBar seekBar;
    private ImageView imgOnOff;
    private MainController controller;

    //**********************GETTER AND SETTER METHODS*******************
    public SensorManager getSensorManager() {
        return sensorManager;
    }

    public SeekBar getSeekBar() {
        return seekBar;
    }

    public ImageView getImgOnOff() {
        return imgOnOff;
    }


    //**********************OVERRIDE METHODS*******************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        controller.initListeners();

    }

    @Override
    public void onResume() {
        super.onResume();
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null)
            controller.accelerometerSetUp();

    }

    @Override
    public void onPause() {
        super.onPause();
        if (controller.isGotSensor())
            sensorManager.unregisterListener(controller.getSensorListener());
    }

    //**********************INSTANCE METHODS*******************
    private void init(){

        imgOnOff = findViewById(R.id.shakerImg);
        seekBar = findViewById(R.id.seek_bar);
        sensorManager = (SensorManager)this.getSystemService(Context.SENSOR_SERVICE);
        controller = new MainController(this);
    }

}
