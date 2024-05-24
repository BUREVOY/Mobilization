package ru.mirea.yudakovam.employeedb;

import android.app.Application;

import androidx.room.Room;

public class App extends Application {
    //Экземпляр приложения и методы для доступа к экземплярам
    public static App instance;
    private AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //слздает экземпляр БД
        database = Room.databaseBuilder(this, AppDatabase.class, "database")
                //выщов функций работы с бд в отдельном потоке, но для нересурсных задач
                //есть allowMainThreadQueries, позволяющий работать в главном потоке
                .allowMainThreadQueries()
                .build();
    }
    //экз приложения
    public static App getInstance() {
        return instance;
    }
    //экз БД
    public AppDatabase getDatabase() {
        return database;
    }
}
