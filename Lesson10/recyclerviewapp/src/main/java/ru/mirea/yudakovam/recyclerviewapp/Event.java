package ru.mirea.yudakovam.recyclerviewapp;

public class Event {
    private String title;
    private String description;
    private int imageResource;

    public Event(String title, String description, int imageResource) {
        this.title = title;
        this.description = description;
        this.imageResource = imageResource;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getImageResource() {
        return imageResource;
    }
}
