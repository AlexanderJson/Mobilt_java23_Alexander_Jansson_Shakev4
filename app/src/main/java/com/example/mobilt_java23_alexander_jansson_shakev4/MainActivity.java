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
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.app.AlarmManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.widget.Toast;



import org.w3c.dom.Text;

import java.io.IOException;


import android.media.MediaPlayer;


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
        ProgressBar progressBar = findViewById(R.id.progressBar2);
        ImageView imageView = findViewById(R.id.imageView3);
        imageView.setVisibility(View.INVISIBLE);
        TextView textView = findViewById(R.id.shakeText);

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

                    double exponent = 2;
                    float rotX = e.values[0];
                    float rotY = e.values[1];
                    float rotZ = e.values[2];
                    float rotation = (float) Math.sqrt(Math.pow(rotX,exponent) + Math.pow(rotY,exponent) + Math.pow(rotZ,exponent));
                    float maxRotation = 2.0f;
                    if(rotation > maxRotation){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                int currentProgress = progressBar.getProgress();
                                progressBar.setMax(100);

                                button.setBackgroundColor(Color.RED);

                                Toast.makeText(MainActivity.this, "Rotatated quickly at:  " + rotation, Toast.LENGTH_SHORT).show();
                                if (currentProgress < progressBar.getMax()){
                                    progressBar.setProgress(currentProgress + 5);

                                }else if(progressBar.getMax() == 100){
                                    progressBar.setProgress(0);
                                    button.setBackgroundColor(Color.BLACK);
                                    imageView.setVisibility(View.VISIBLE);

                                }
                            }
                        });
                    }

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



