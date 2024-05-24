package ru.mirea.yudakovam.mireaproject;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SensorLogicFragment extends Fragment implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor sensorOrientation;
    private TextView textViewDirection;
    public SensorLogicFragment() {
        // Обязательно
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Заполняем
        View view = inflater.inflate(R.layout.fragment_sensorlogic, container, false);
        // Инициализируем элементы пользовательского интерфейса и обработчики событий
        initializeViews(view);
        return view;
    }

    private void initializeViews(View view) {
        textViewDirection = view.findViewById(R.id.textView_direction);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Получаем экземпляр SensorManager
        sensorManager = (SensorManager) requireActivity().getSystemService(requireActivity().SENSOR_SERVICE);
        // Получаем датчик ориентации
        sensorOrientation = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        if (sensorOrientation != null) {
            // Регистрируем слушателя датчика ориентации
            sensorManager.registerListener(this, sensorOrientation, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        // Отписываемся от обновлений датчика при уходе из фрагмента
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // Получаем азимут (угол поворота относительно севера) из данных с датчика ориентации
        float azimuthRadians = event.values[0]; // Получаем значение в радианах
        float azimuthDegrees = (float) Math.toDegrees(azimuthRadians); // Переводим в градусы

        // Проверяем и корректируем диапазон азимута(между направлением на север и другим) от 0 до 360 градусов
        if (azimuthDegrees < 0) {
            azimuthDegrees += 360;
        }

        String[] directions = {
                "Север", "Северо-Восток", "Восток", "Юго-Восток",
                "Юг", "Юго-Запад", "Запад", "Северо-Запад"
        };

        double[] angles = {
                22.5, 67.5, 112.5, 157.5, 202.5, 247.5, 292.5, 337.5
        };

        String direction = "Север";  // Default direction in case no match is found

        for (int i = 0; i < angles.length; i++) {
            if (azimuthDegrees < angles[i]) {
                direction = directions[i];
                break;
            }
        }
        if (azimuthDegrees >= 337.5) {
            direction = "Север";
        }

        // Отображаем направление на экране
        textViewDirection.setText("Телефон показывает на" + direction);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Метод обратного вызова, вызывается при изменении точности датчика
    }
}
