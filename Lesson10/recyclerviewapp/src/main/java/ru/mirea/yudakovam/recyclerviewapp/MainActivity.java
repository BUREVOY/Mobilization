package ru.mirea.yudakovam.recyclerviewapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Event> events = new ArrayList<>();
        events.add(new Event("Крещение руси", "988 ГОД", R.drawable.flag_of_russia));
        events.add(new Event("Первое упоминание о Москве", "1147 ГОД", R.drawable.moscow));

        EventAdapter adapter = new EventAdapter(events);
        recyclerView.setAdapter(adapter);
    }
}