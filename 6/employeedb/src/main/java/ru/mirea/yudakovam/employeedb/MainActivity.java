package ru.mirea.yudakovam.employeedb;


import androidx.appcompat.app.AppCompatActivity;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import ru.mirea.yudakovam.employeedb.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding	=	ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        //создание бд
        AppDatabase db = App.getInstance().getDatabase();
        //создание ДАО
        EmployeeDao employeeDao = db.employeeDao();
        //создание энтити
        Employee employee = new Employee();

        employee.id = 5;
        employee.name = "Человек-воробей";
        employee.power = "скорость";
        employeeDao.insert(employee);
//        List<Employee> employees = employeeDao.getAll();
        employee = employeeDao.getById(1);
        employee.power = "полет";
        employeeDao.update(employee);
        Log.d(TAG, employee.name + " " + employee.power + "--------------------");
    }
}
