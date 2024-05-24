package ru.mirea.yudakovam.lesson6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import ru.mirea.yudakovam.lesson6.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private SharedPreferences.Editor	editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding	=	ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //MODE_PRIVATE для ограничения доступа к файлу настроек только родительского приложения
        SharedPreferences sharedPref = getSharedPreferences("mirea_settings",	Context.MODE_PRIVATE);
        //editor - запись ключ-значение
        editor	=	sharedPref.edit();

        //дефолтные значения(при повторном запуске берутся старые)
        binding.editTextGroup.setText(sharedPref.getString("GROUP", ""));
        binding.editTextList.setText(sharedPref.getString("LIST", ""));
        binding.editTextFilm.setText(sharedPref.getString("SERIES", ""));
    }

    public void SaveData(View view){
        editor.putString("GROUP", binding.editTextGroup.getText().toString());
        editor.putString("LIST", binding.editTextList.getText().toString());
        editor.putString("SERIES ", binding.editTextFilm.getText().toString());
        //для сохранения пар
        editor.apply();
    }
}