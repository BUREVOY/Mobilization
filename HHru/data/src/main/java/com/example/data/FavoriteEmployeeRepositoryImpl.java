package com.example.data;

import android.util.Log;

import com.example.data.Models.EmployeeEntity;
import com.example.domain.Models.FavoriteEmployee;
import com.example.domain.Models.Employee;
import com.example.domain.Repositories.FavoriteEmployeeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FavoriteEmployeeRepositoryImpl implements FavoriteEmployeeRepository {
    private final EmployeeDao employeeDao;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();


    public FavoriteEmployeeRepositoryImpl(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @Override
    public void addFavoriteEmployee(FavoriteEmployee employee) {
        EmployeeEntity entity = new EmployeeEntity(
                employee.getId(),
                employee.getName(),
                employee.getAvatar(),
                employee.getExperience(),
                employee.getExpectedPosition()
        );

        // Асинхронное выполнение операции вставки
        executorService.execute(() -> {
            employeeDao.insert(entity);
            Log.d("FavoriteEmployeeRepository", "Employee added to favorites: " + employee.getName());
        });
    }


    @Override
    public List<Employee> getAllFavoriteEmployee() {
        // Получение всех сотрудников из избранного
        List<EmployeeEntity> entities = employeeDao.getAllEmployees();
        Log.d("FavoriteEmployeeRepository", "Found " + entities.size() + " employees.");
        List<Employee> employees = new ArrayList<>();
        for (EmployeeEntity entity : entities) {
            employees.add(new Employee(
                    entity.getName(),
                    entity.getAvatar(),
                    entity.getExperience(),
                    entity.getExpected_position(),
                    entity.getId()
            ));
        }
        return employees;
    }

    @Override
    public void deleteFavoriteEmployee(int employeeId) {
        employeeDao.deleteEmployee(employeeId);
    }

    @Override
    public boolean isFavorite(int employeeId) {
        EmployeeEntity entity = employeeDao.getEmployeeById(employeeId);
        return entity != null;
    }

    @Override
    public void deleteAllEmployees() {
        employeeDao.deleteAllEmployees();
    }

    public Employee convertToEmployee(EmployeeEntity entity) {
        // Преобразуем EmployeeEntity в Employee
        if (entity != null) {
            return new Employee(entity.getId(), entity.getName(), entity.getAvatar(), entity.getExperience(), entity.getExpected_position());
        }
        return null;
    }

    @Override
    public Employee getEmployeeById(int employeeId) {
        EmployeeEntity entity = employeeDao.getEmployeeById(employeeId);
        // Преобразуем EmployeeEntity в Employee
        return convertToEmployee(entity);
    }
}
