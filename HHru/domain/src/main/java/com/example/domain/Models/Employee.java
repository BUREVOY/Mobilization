package com.example.domain.Models;

public class Employee {
    private String name;              // Имя кандидата
    private String avatar;            // URL аватара кандидата
    private String experience;        // Опыт работы кандидата (в годах)
    private String expectedPosition;  // Ожидаемая должность
    private String id;                // Идентификатор кандидата (строковый)

    // Конструктор с параметрами
    public Employee(String name, String avatar, String experience, String expectedPosition, String id) {
        this.name = name;
        this.avatar = avatar;
        this.experience = experience;
        this.expectedPosition = expectedPosition;
        this.id = id;
    }

    // Геттеры и сеттеры
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

    public String getExpectedPosition() {
        return expectedPosition;
    }

    public void setExpectedPosition(String expectedPosition) {
        this.expectedPosition = expectedPosition;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
