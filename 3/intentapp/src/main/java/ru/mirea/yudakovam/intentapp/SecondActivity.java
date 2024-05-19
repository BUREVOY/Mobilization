package ru.mirea.yudakovam.intentapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.TextView;

import ru.mirea.yudakovam.intentapp.databinding.ActivitySecondBinding;

public class SecondActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        String text = (String) getIntent().getSerializableExtra("key");
        TextView tv = findViewById(R.id.textView);

        tv.setText(text);
    }
}