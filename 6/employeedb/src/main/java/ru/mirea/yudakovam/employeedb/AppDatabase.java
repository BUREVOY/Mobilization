package ru.mirea.yudakovam.employeedb;

import androidx.room.Database;
import androidx.room.RoomDatabase;

//@Database связывает дао и энтити
@Database(entities = {Employee.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract EmployeeDao employeeDao();
}
