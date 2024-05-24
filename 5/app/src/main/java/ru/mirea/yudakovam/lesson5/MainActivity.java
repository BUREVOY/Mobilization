package ru.mirea.yudakovam.lesson5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ru.mirea.yudakovam.lesson5.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements SensorEventListener
{

    private ActivityMainBinding binding;
    @Override
    protected	void	onCreate(Bundle	savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //возвращаем список всех сенсоров
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        //привязывает listView
        ListView listSensor = binding.listView;
        //	создаем	список пар ключ-значение для	отображения	в	ListView	найденных	датчиков
        ArrayList<HashMap<String, Object>> arrayList = new ArrayList<>();
        //для каждого датчика добаляем название и диапазон
        for (int i = 0; i < sensors.size(); i++) {
            HashMap<String, Object> sensorTypeList = new HashMap<>();
            sensorTypeList.put("Name", sensors.get(i).getName());
            sensorTypeList.put("Value", sensors.get(i).getMaximumRange());
            arrayList.add(sensorTypeList);
        }
        //	создаем	адаптер	и	устанавливаем	тип	адаптера	- отображение	двух	полей
        SimpleAdapter mHistory =
                new SimpleAdapter(this, arrayList, android.R.layout.simple_list_item_2,
                        //ключи из hashmap
                        new String[]{"Name", "Value"},
                        //id полей, которые будут заполнены значениями
                        new int[]{android.R.id.text1, android.R.id.text2});
        listSensor.setAdapter(mHistory);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            //х, лево правно
            float valueAzimuth = event.values[0];
            //У, вверх вниз
            float valuePitch = event.values[1];
            //z, к ползователю, от него
            float valueRoll = event.values[2];
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}