package ru.mirea.yudakovam.notebook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import ru.mirea.yudakovam.notebook.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding	=	ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
    //проверяем внешнее хранилище на доступность записи
    public boolean isExternalStorageWritable() {
        //текущее состояние внешнего хранилища
        String state = Environment.getExternalStorageState();
        //проверяет монтировано ли оно и доступно ли для записи
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }
    //доступность на чтение
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        //проверяет монт или нет и доступно ли в режиме только для чтения
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }
    public void SaveData(View view){
        //доступно ли для записи
        if (isExternalStorageWritable()) {
            String fileName = binding.editTextFileName.getText().toString();
            String quote = binding.editTextQuote.getText().toString();

            //публичная директория документов
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
            //файл для хранения по пути
            File file = new File(path, fileName +".txt");
            try {
                //поток для записи в файл
                FileOutputStream fileOutputStream = new FileOutputStream(file.getAbsoluteFile());
                OutputStreamWriter output = new OutputStreamWriter(fileOutputStream);

                //запись, закрытие поток, вывод сообщениея
                output.write(quote);
                output.close();
                Toast.makeText(MainActivity.this, "смотреть в /storage/emulated/0/Documents", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                Toast.makeText(this, "Error saving data", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void LoadData(View view){
        //доступ на чтение
        if (isExternalStorageReadable()){
            //публичная директория документов

            File path = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOCUMENTS);
            File file = new File(path, binding.editTextFileName.getText().toString() + ".txt");
            try	{
                //поток для чтения из файла и из потока
                FileInputStream fileInputStream = new FileInputStream(file.getAbsoluteFile());
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
                //список строк
                List<String> lines = new ArrayList<String>();
                //для буферизации(временное хранение) чтения данных
                BufferedReader reader = new BufferedReader(inputStreamReader);
                //чтение строки
                String line = reader.readLine();
                //чтение всех строк
                while (line != null) {
                    lines.add(line);
                    line = reader.readLine();
                }
                binding.editTextQuote.setText(lines.get(0).toString());
                Toast.makeText(this, "Data loaded", Toast.LENGTH_SHORT).show();
            }	catch	(Exception	e)	{
                Toast.makeText(this, "Error loading data", Toast.LENGTH_SHORT).show();
            }
        }
    }
}