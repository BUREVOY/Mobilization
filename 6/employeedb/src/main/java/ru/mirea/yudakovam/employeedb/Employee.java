package ru.mirea.yudakovam.employeedb;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

//@Entity чтобы создалась таблица с тем же именем что и в классе
//@Entity - сущность, отвечает за таблицу, каждый экземпляр - строка в таблице
@Entity
public class Employee {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public String name;
    public String power;
}
