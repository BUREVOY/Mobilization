package com.example.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.data.Models.EmployeeEntity;

import java.util.List;

@Dao
public interface EmployeeDao {
    // Вставка нового сотрудника в базу
    @Insert
    void insert(EmployeeEntity employee);

    @Query("SELECT * FROM employees")
    List<EmployeeEntity> getAllEmployees();

    // Удалить сотрудника по его ID
    @Query("DELETE FROM employees WHERE id = :employeeId")
    void deleteEmployee(int employeeId);

    // Получить сотрудника по его имени
    @Query("SELECT * FROM employees WHERE name = :name LIMIT 1")
    EmployeeEntity getEmployeeByName(String name);

    // Получить сотрудника по его ID
    @Query("SELECT * FROM employees WHERE id = :id LIMIT 1")
    EmployeeEntity getEmployeeById(int id);

    @Query("DELETE FROM employees")
    void deleteAllEmployees();
}

