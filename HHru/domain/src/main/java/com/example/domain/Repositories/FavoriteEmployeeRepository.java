package com.example.domain.Repositories;

import com.example.domain.Models.Employee;
import com.example.domain.Models.FavoriteEmployee;

import java.util.List;

public interface FavoriteEmployeeRepository {
    void addFavoriteEmployee(FavoriteEmployee employee);  // Добавление сотрудника в штат
    List<Employee> getAllFavoriteEmployee();
    void deleteFavoriteEmployee(int EmployeeId);
    boolean isFavorite(int EmployeeId);
    void deleteAllEmployees();
    Employee getEmployeeById(int employeeId);
}
