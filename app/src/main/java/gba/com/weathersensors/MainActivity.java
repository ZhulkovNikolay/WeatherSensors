package gba.com.weathersensors;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    SensorManager manager;
    SensorEventListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }


    @Override
    protected void onResume() {
        final TextView ambiendText = (TextView) findViewById(R.id.ambiendText);
        final TextView humidityText = (TextView) findViewById(R.id.humidityText);

        super.onResume();
        manager = (SensorManager)getSystemService(SENSOR_SERVICE);
        List<Sensor> sensors = manager.getSensorList(Sensor.TYPE_ALL);
        for (Sensor sensor: sensors) {
            Log.i("SENSORS",sensor.getName());
        }
        Sensor ambientTemperatureSensor = manager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        Sensor absoluteHumidity = manager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
        if(ambientTemperatureSensor == null) return;
        if (absoluteHumidity == null) return;
        listener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                Log.i("SENSORS","ambient temperature = " + sensorEvent.values[0]);
                ambiendText.setText("ambient temperature =  " + sensorEvent.values[0]);
                Log.i("SENSORS","absolute Humidity = " + sensorEvent.values[1]);
                humidityText.setText("absolute Humidity =  " + sensorEvent.values[1]);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
            }
        };
        manager.registerListener(listener, ambientTemperatureSensor,50);
        manager.registerListener(listener, absoluteHumidity, 50);


    }

    @Override
    protected void onPause() {
        if (listener != null){
            manager.unregisterListener(listener);
            listener = null;
        }
        super.onPause();
    }
}
