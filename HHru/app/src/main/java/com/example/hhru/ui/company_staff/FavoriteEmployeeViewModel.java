package com.example.hhru.ui.company_staff;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.domain.Models.Employee;
import com.example.domain.Repositories.FavoriteEmployeeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class FavoriteEmployeeViewModel extends ViewModel {
    private final FavoriteEmployeeRepository favoriteEmployeeRepository;
    private final MutableLiveData<List<Employee>> favoriteEmployees; // Используем MutableLiveData
    private final Executor executor; // Executor для работы с потоками

    public FavoriteEmployeeViewModel(FavoriteEmployeeRepository favoriteEmployeeRepository) {
        this.favoriteEmployeeRepository = favoriteEmployeeRepository;
        favoriteEmployees = new MutableLiveData<>(); // Инициализация MutableLiveData
        executor = Executors.newSingleThreadExecutor(); // Создаем один поток для всех операций
        loadFavoriteEmployees();
    }

    public LiveData<List<Employee>> getFavoriteEmployees() {
        return favoriteEmployees;
    }

    public void removeFavoriteEmployee(int employeeId) {
        executor.execute(() -> {
            favoriteEmployeeRepository.deleteFavoriteEmployee(employeeId);
            loadFavoriteEmployees(); // Перезагружаем список после удаления
        });
    }

    public void loadFavoriteEmployees() {
        executor.execute(() -> {
            try {
                List<Employee> employees = favoriteEmployeeRepository.getAllFavoriteEmployee();
                Log.d("FavoriteEmployeeViewModel", "Loaded favorite employees: " + employees.size());
                if (employees != null && !employees.isEmpty()) {
                    favoriteEmployees.postValue(employees);
                } else {
                    Log.d("FavoriteEmployeeViewModel", "No favorite employees found in database.");
                    favoriteEmployees.postValue(new ArrayList<>());
                }
            } catch (Exception e) {
                Log.e("FavoriteEmployeeViewModel", "Error loading favorite employees", e);
                favoriteEmployees.postValue(new ArrayList<>());
            }
        });
    }

}
