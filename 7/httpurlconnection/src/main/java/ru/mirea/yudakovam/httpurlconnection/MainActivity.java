package ru.mirea.yudakovam.httpurlconnection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import ru.mirea.yudakovam.httpurlconnection.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getSystemService возвращает системный сервис для управления подключением к сети
                //ConnectivityManager класс, который управляет сетевым соединением и
                //предоставляет инфу о подключении
                ConnectivityManager connectivityManager =
                        (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                //инфа о сети
                NetworkInfo networkInfo = null;
                //это нужно чтобы системный сервис был успешно получен
                if (connectivityManager != null) {
                    //данные о состоянии опдключения(тип(wifi), подключено\отключено)
                    networkInfo = connectivityManager.getActiveNetworkInfo();
                }

                if (networkInfo != null && networkInfo.isConnected()) {
                    //устройство подключено к сети
                    new DownloadPageTask().execute("https://ipinfo.io/json"); // запуск нового потока
                } else {
                    //не подключено к сети
                    Toast.makeText(MainActivity.this, "Нет интернета", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public class DownloadPageTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            binding.textViewLocate.setText("Загрузка...");
        }

        @Override
        protected String doInBackground(String... urls) {
            try {
                return downloadIpInfo(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
                return "Error";
            }
        }


        //json попадает сюда после downloadIpInfo
        @Override
        protected void onPostExecute(String result) {

            try {
                JSONObject responseJSON = new JSONObject(result);

                // выводим локацию
                String locate = "Город: " + responseJSON.getString("city") +
                        "\nРегион: " + responseJSON.getString("region") +
                        "\nСтрана: " + responseJSON.getString("country");

                //вызов DownloadWeatherTask
                new DownloadWeatherTask().execute("https://api.open-meteo.com/v1/forecast?latitude=" +
                        52.52 +
                        "&longitude=" +
                        13.41 +
                        "&current_weather=true");
                //нижний текст появляется
                binding.textViewLocate.setText(locate);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            super.onPostExecute(result);
        }

        private String downloadIpInfo(String address) throws IOException {
            //для чтения данных из входящего потока
            InputStream inputStream = null;
            //для данных
            String data = "";
            try {
                URL url = new URL(address);
                //открывается соединение
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                //таймаут чтения
                connection.setReadTimeout(100000);
                //таймаут подключенияк серверу
                connection.setConnectTimeout(100000);
                connection.setRequestMethod("GET");
                //флаг для следования за редиректом
                connection.setInstanceFollowRedirects(true);
                //не юзать кэш
                connection.setUseCaches(false);
                //приложению дозволено читать данные с соединения
                connection.setDoInput(true);
                //ответ сервера
                int responseCode = connection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    //поток ввода данных из ответа серва
                    inputStream = connection.getInputStream();
                    //буферный поток
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    int read = 0;
                    //цикл для считывания данных из потока в буфер(когда все байты прочитаны, будет -1)
                    while ((read = inputStream.read()) != -1) {
                        bos.write(read);
                    }
                    bos.close();
                    //буфер в строку
                    data = bos.toString();
                } else {
                    data = connection.getResponseMessage() + "Error Code " + responseCode;
                }
                connection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                //независимо от завершения выполнится
                if (inputStream != null) {
                    inputStream.close();
                }
            }
            //возвращение данных в виде строки
            return data;
        }
    }

    //выводим погоду
    public class DownloadWeatherTask extends DownloadPageTask {

        @Override
        protected void onPreExecute() {
            //сверху появляется загрузка
            super.onPreExecute();
            binding.textViewWeather.setText("Загрузка...");

        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject responseJSON = new JSONObject(result);
                JSONObject currentWeather = responseJSON.getJSONObject("current_weather");
                // вывводим содержимое json сверху
                String weather = "Температура: " + currentWeather.getString("temperature") + "°C" +
                        "\nСкорость ветра: " + currentWeather.getString("windspeed") + "km/h" +
                        "\nНаправление ветра: " + currentWeather.getString("winddirection") + "°";
                binding.textViewWeather.setText(weather);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            super.onPostExecute(result);
        }
    }
}