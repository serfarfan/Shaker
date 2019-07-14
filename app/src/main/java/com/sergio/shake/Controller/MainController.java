package com.sergio.shake.Controller;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.SeekBar;

import com.sergio.shake.R;
import com.sergio.shake.View.MainActivity;

public class MainController {

    private MainActivity mainActivity;
    private float shakeValue, currentValue, lastValue, deltaValue;
    private boolean gotSensor = false, onOff = true;
    private SensorEventListener sensorListener;

    //**********************GETTER AND SETTER METHODS*******************
    public boolean isGotSensor() {
        return gotSensor;
    }

    public SensorEventListener getSensorListener() {
        return sensorListener;
    }

    //*****************CONSTRUCTOR*************************
    public MainController(MainActivity activity){
        mainActivity = activity;
    }

    //**********************INSTANCE METHODS*******************
    public void initListeners(){

        shakeValue = 8;
        sensorListener = new SensorEventListener() {

            @Override
            public void onSensorChanged(SensorEvent event) {

                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];
                lastValue = currentValue;
                currentValue = (float) Math.sqrt((double) (x * x + y * y + z * z));
                float delta = currentValue - lastValue;
                deltaValue = deltaValue * 0.9f + delta;
                //Validate intensity
                if (deltaValue > shakeValue) {
                    //Is on, so turn off
                    if (onOff) {
                        mainActivity.getImgOnOff().
                                setImageResource(R.mipmap.round_turned_in_not_black_48);
                        onOff = false;
                    } else {//Is off, so turn on
                        mainActivity.getImgOnOff().
                                setImageResource(R.mipmap.round_turned_in_black_48);
                        onOff = true;
                    }
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        seekBarSetUp();
        accelerometerSetUp();
    }

    public void accelerometerSetUp() {

        gotSensor = true;
        mainActivity.getSensorManager().registerListener(sensorListener,
                mainActivity.getSensorManager().getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        currentValue = SensorManager.GRAVITY_EARTH;
        lastValue = SensorManager.GRAVITY_EARTH;
        deltaValue = 0f;
    }

    private void seekBarSetUp() {

        mainActivity.getSeekBar().setProgress(20);
        mainActivity.getSeekBar().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                shakeValue = (float) (100 - progress) / 10;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}
