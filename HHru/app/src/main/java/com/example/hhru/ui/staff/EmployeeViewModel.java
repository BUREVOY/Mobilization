package com.example.hhru.ui.staff;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.data.AppDatabase;
import com.example.data.EmployeeDao;
import com.example.data.EmployeeRepositoryImpl;
import com.example.data.FavoriteEmployeeRepositoryImpl;
import com.example.domain.Models.Employee;
import com.example.domain.Models.FavoriteEmployee;
import com.example.domain.Repositories.EmployeeRepository;
import com.example.domain.Repositories.FavoriteEmployeeRepository;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmployeeViewModel extends AndroidViewModel {
    private final EmployeeRepository employeeRepository;
    private final FavoriteEmployeeRepository favoriteEmployeeRepository;
    private final MutableLiveData<List<Employee>> employees = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isFavoriteLiveData = new MutableLiveData<>();
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public EmployeeViewModel(@NonNull Application application, FavoriteEmployeeRepository favoriteEmployeeRepository) {
        super(application);

        // Получаем контекст из Application
        AppDatabase database = AppDatabase.getInstance(application);
        EmployeeDao employeeDao = database.employeeDao();

        // Инициализируем репозитории
        this.employeeRepository = new EmployeeRepositoryImpl();
        this.favoriteEmployeeRepository = favoriteEmployeeRepository;
    }

    public LiveData<List<Employee>> getEmployees() {
        return employees;
    }

    public void loadEmployees() {
        employeeRepository.getEmployeeAsync(new EmployeeRepository.EmployeeCallback() {
            @Override
            public void onSuccess(List<Employee> employeeList) {
                if (employeeList != null) {
                    employees.postValue(employeeList);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("EmployeeViewModel", "Failed to load employees: " + t.getMessage());
            }
        });
    }

    // Добавление сотрудника в избранное с проверкой существования
    public void addFavoriteEmployee(FavoriteEmployee favoriteEmployee) {
        executorService.execute(() -> {
            // Проверка, существует ли уже сотрудник с таким id
            if (favoriteEmployeeRepository.getEmployeeById(Integer.parseInt(favoriteEmployee.getId())) == null) {
                // Сотрудник не найден, добавляем его в избранное
                favoriteEmployeeRepository.addFavoriteEmployee(favoriteEmployee);
                Log.d("FavoriteEmployeeViewModel", "Employee added to favorites: " + favoriteEmployee.getName());
            } else {
                // Сотрудник уже существует в избранном
                Log.d("FavoriteEmployeeViewModel", "Employee already exists in favorites: " + favoriteEmployee.getName());
            }
        });
    }


    // Удаление сотрудника из избранного
    public void deleteFavoriteEmployee(String employeeId) {
        executorService.execute(() -> favoriteEmployeeRepository.deleteFavoriteEmployee(Integer.parseInt(employeeId)));
    }

    // Проверка, является ли сотрудник избранным
    public LiveData<Boolean> isFavorite(String employeeId) {
        executorService.execute(() -> {
            boolean isFavorite = favoriteEmployeeRepository.isFavorite(Integer.parseInt(employeeId));
            new Handler(Looper.getMainLooper()).post(() -> isFavoriteLiveData.setValue(isFavorite));
        });
        return isFavoriteLiveData;
    }
}
