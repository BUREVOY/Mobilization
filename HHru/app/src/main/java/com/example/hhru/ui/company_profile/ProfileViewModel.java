package com.example.hhru.ui.company_profile;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProfileViewModel extends ViewModel {

    private final MutableLiveData<String> email;

    public ProfileViewModel(Context context) {
        email = new MutableLiveData<>();
        loadEmailFromPreferences(context); // Загружаем email из SharedPreferences
    }

    private void loadEmailFromPreferences(Context context) {
        // Получаем SharedPreferences
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);

        // Читаем email из SharedPreferences (если нет, возвращаем null или строку по умолчанию)
        String savedEmail = sharedPreferences.getString("email", "No email found");

        // Обновляем LiveData с полученным email
        email.setValue(savedEmail);
    }

    public LiveData<String> getEmail() {
        return email;
    }
}
