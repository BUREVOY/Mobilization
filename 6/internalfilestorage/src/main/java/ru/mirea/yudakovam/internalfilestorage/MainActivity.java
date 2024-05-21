package ru.mirea.yudakovam.internalfilestorage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import java.io.FileOutputStream;

import ru.mirea.yudakovam.internalfilestorage.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private String fileName = "data_birthday.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding	=	ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    public void SaveData(View view){
        FileOutputStream outputStream;
        try {
            outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(binding.editTextText.getText().toString().getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}