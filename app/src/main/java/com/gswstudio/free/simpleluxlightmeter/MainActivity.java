package com.gswstudio.free.simpleluxlightmeter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

  TextView maksimal, pembacaan;
  ProgressBar mBar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    mBar = findViewById(R.id.progressbar_sensor);
    maksimal = findViewById(R.id.textview_maksimal);
    pembacaan = findViewById(R.id.textview_pembacaan);

//    Sensor Manager
    SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    assert sensorManager != null;
    Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

//    cek sensor di device. Jika ada langsung baca
    if (sensor == null) {
      Toast.makeText(this, "Tidak Ada Sensor", Toast.LENGTH_SHORT).show();
    } else {
      float max = sensor.getMaximumRange();
      mBar.setMax((int) max);
      maksimal.setText("Maksimal: " + String.valueOf(max));
      sensorManager
          .registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }
  }

  SensorEventListener sensorEventListener = new SensorEventListener() {
    @SuppressLint("SetTextI18n")
    @Override
    public void onSensorChanged(SensorEvent event) {
      if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
        float v = event.values[0];
        mBar.setProgress((int) v);
        pembacaan.setText(String.valueOf(v) + " Lux");
      }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
  };
}
