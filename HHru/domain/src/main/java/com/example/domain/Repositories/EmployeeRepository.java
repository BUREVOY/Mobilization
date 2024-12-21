package com.example.domain.Repositories;

import com.example.domain.Models.Employee;

import java.util.List;

public interface EmployeeRepository {
    void getEmployeeAsync(EmployeeCallback callback);

    interface EmployeeCallback {
        void onSuccess(List<Employee> employees);
        void onFailure(Throwable t);
    }
}

