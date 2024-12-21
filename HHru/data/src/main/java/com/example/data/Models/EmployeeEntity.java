package com.example.data.Models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "employees") // Имя таблицы "employees"
public class EmployeeEntity {
    @PrimaryKey
    @NonNull
    private String id; // Используем String для ID, как в ваших данных
    private String name;
    private String avatar;
    private String experience;
    private String expected_position;

    // Пустой конструктор, необходим для Room
    public EmployeeEntity() {
    }

    // Конструктор без id (для создания новых записей)
    public EmployeeEntity(String id, String name, String avatar, String experience, String expected_position) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
        this.experience = experience;
        this.expected_position = expected_position;
    }

    // Геттеры и сеттеры
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getExpected_position() {
        return expected_position;
    }

    public void setExpected_position(String expected_position) {
        this.expected_position = expected_position;
    }
}
