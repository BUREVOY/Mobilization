package ru.mirea.yudakovam.timeservice;

import androidx.appcompat.app.AppCompatActivity;


import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;


import ru.mirea.yudakovam.timeservice.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private final String host = "time.nist.gov"; // или time-a.nist.gov
    private final int port = 13;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //создает и запускает GetTimeTask
                GetTimeTask timeTask = new GetTimeTask();
                timeTask.execute();
            }
        });
    }
    private class GetTimeTask extends AsyncTask<Void, Void, String> {
        //операции в фоновом режиме
        @Override
        protected String doInBackground(Void... params) {
            String timeResult = "";
            try {
                //сокет соединение
                Socket socket = new Socket(host, port);
                //создаем буфер (SocketUtils)
                BufferedReader reader = SocketUtils.getReader(socket);
                reader.readLine(); // игнорируем первую строку
                timeResult = reader.readLine(); // считываем вторую строку
                Log.d("Socket", timeResult);
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //это принимает onPostExecute
            return timeResult;
        }
        //выполняется в основном потоке, после завершения фоновой задачи
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //обновляет текст
            binding.textView.setText(result);
        }
    }
}