package ru.mirea.yudakovam.camera;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ru.mirea.yudakovam.camera.databinding.ActivityMainBinding;

public	class	MainActivity	extends	AppCompatActivity {
    private static final int REQUEST_CODE_PERMISSION = 100;
    private static final int CAMERA_REQUEST = 0;
    private boolean isWork = false;
    private Uri imageUri;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //самопроверка разрешения, если разрешено, тру, нет, запрос
        int	cameraPermissionStatus = ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.CAMERA);
        int	storagePermissionStatus = ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if	(cameraPermissionStatus == PackageManager.PERMISSION_GRANTED && storagePermissionStatus
                ==	PackageManager.PERMISSION_GRANTED) {
            isWork = true;
        } else {
            //	Выполняется запрос к пользователь на получение всех необходимых разрешений
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION);
        }

        //	Создание функции обработки результата от системного приложения «камера»
        ActivityResultCallback<ActivityResult> callback = new ActivityResultCallback<ActivityResult>() {
            //обновляет imageView если результат успешен
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    binding.imageView.setImageURI(imageUri);
                }
            }
        };

        //создание лаунчера
        ActivityResultLauncher<Intent> cameraActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                callback);

        //	Обработчик нажатия на компонент «imageView»
        binding.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //создаем неявный интент
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //	проверка на наличие разрешений для камеры
                if (isWork) {
                    try {
                        //создает временный файл для хранения фотографии
                        File photoFile = createImageFile();

                        //	генерирование безопасного пути(URI) к файлу на основе authorities
                        //authorities = строка(пакетное имя), которая идентифицирует fileprovider
                        String authorities = getApplicationContext().getPackageName() + ".fileprovider";
                        imageUri = FileProvider.getUriForFile(MainActivity.this, authorities, photoFile);
                        //передача ури в интент
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        cameraActivityResultLauncher.launch(cameraIntent);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    private File createImageFile() throws IOException {
        //генерация имени файла на основе даты и времени
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date());
        String imageFileName = "IMAGE_" + timeStamp + "_";
        //директория для хранения фала
        File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        //создание файла в директории
        return File.createTempFile(imageFileName, ".jpg", storageDirectory);
    }

    //метод для ответа на запрос о разрешении
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)	{
        //	производится проверка полученного результата от пользователя на запрос разрешения Camera
        //проверка с помощью базового класса
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //идентификация запроса разрешения
        if	(requestCode == REQUEST_CODE_PERMISSION) {
            //	проверка на наличие хотя бы одного элемента(если юзер просто закрыл окнои массив пустой)
            isWork = grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED;
        }
    }

}