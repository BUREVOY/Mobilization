package ru.mirea.yudakovam.employeedb;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

// DATA ACCESS OBJECT для добавления, удаления, формирования запросов
// DATA ACCESS OBJECT отвечает за методы
@Dao
public interface EmployeeDao {
    //запросы проверяются на этапе компиляции
    @Query("SELECT * FROM employee")
    List<Employee> getAll();
    @Query("SELECT * FROM employee WHERE id = :id")
    Employee getById(long id);
    @Insert
    void insert(Employee employee);
    @Update
    void update(Employee employee);
    @Delete
    void delete(Employee employee);
}
