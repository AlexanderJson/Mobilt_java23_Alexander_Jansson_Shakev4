package com.example.mobilt_java23_alexander_jansson_shakev4;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        }
        );

        SensorManager sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor accelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        Sensor gyroscope = sm.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        Button button = findViewById(R.id.ShakeButton);
        TextView text = findViewById(R.id.ShakeText);

        SensorEventListener sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent e) {
                if(e.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
                    float x = e.values[0];
                    float y = e.values[1];
                    float z = e.values[2];
                    button.setText(String.format("X: %.2f, Y: %.2f, Z: %.2f", x,y,z));

                }

                if (e.sensor.getType() == Sensor.TYPE_GYROSCOPE){
                    float rotX = e.values[0];
                    float rotY = e.values[1];
                    float rotZ = e.values[2];
                    text.setText(String.format("X: %.2f, Y: %.2f, Z: %.2f", rotX, rotY, rotZ));

                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }

        };
        sm.registerListener(sensorEventListener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        sm.registerListener(sensorEventListener, gyroscope, SensorManager.SENSOR_DELAY_NORMAL);

        }
    }

