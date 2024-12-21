package com.example.domain.Models;

public class FavoriteEmployee {
    private String name;              // Имя сотрудника
    private String avatar;            // URL аватара сотрудника
    private String experience;        // Опыт работы сотрудника (в годах)
    private String expectedPosition;  // Ожидаемая должность
    private String id;                // Идентификатор сотрудника (строковый)

    public FavoriteEmployee(String name, String avatar, String experience, String expectedPosition, String id) {
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

