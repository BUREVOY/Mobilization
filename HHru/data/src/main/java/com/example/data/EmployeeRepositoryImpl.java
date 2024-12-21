package com.example.data;

import android.util.Log;

import com.example.domain.Models.Employee;
import com.example.domain.Repositories.EmployeeRepository;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeRepositoryImpl implements EmployeeRepository {

    private static final String TAG = "EmployeeRepositoryImpl"; // Тег для логирования

    @Override
    public void getEmployeeAsync(EmployeeCallback callback) {
        // Асинхронный вызов через Retrofit
        NetworkApi.EmployeeService service = NetworkApi.getClient().create(NetworkApi.EmployeeService.class);
        Call<List<Employee>> call = service.getEmployees(); // Ожидаем список сотрудников

        Log.d(TAG, "Sending request to get Employees"); // Логируем начало запроса

        call.enqueue(new Callback<List<Employee>>() {
            @Override
            public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Данные успешно получены, передаем их в callback
                    Log.d(TAG, "Employees successfully retrieved, count: " + response.body().size()); // Логируем успешный ответ
                    callback.onSuccess(response.body());
                } else {
                    // Ответ не успешен
                    Log.e(TAG, "Response unsuccessful, code: " + response.code()); // Логируем код ошибки
                    callback.onFailure(new Exception("Response unsuccessful"));
                }
            }

            @Override
            public void onFailure(Call<List<Employee>> call, Throwable t) {
                // Ошибка при вызове API
                Log.e(TAG, "API call failed", t); // Логируем ошибку
                callback.onFailure(t);
            }
        });
    }
}
