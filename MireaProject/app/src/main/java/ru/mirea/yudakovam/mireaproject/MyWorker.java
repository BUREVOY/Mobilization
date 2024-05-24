package ru.mirea.yudakovam.mireaproject;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.concurrent.TimeUnit;

public class MyWorker extends Worker {
    private static final String TAG = "MyWorker";

    public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d(TAG, "Starting background task...");

        // Моделируем выполнение работы
        for (int i = 0; i < 5; i++) {
            try {
                // Подождем 2 секунды перед каждой итерацией
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return Result.failure(); // Возвращаем результат выполнения задачи
            }

            Log.d(TAG, "Background task iteration " + (i + 1));
        }

        Log.d(TAG, "Background task completed.");

        // Указываем успешный результат выполнения задачи
        return Result.success();
    }
}