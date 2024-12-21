package com.example.hhru.ui.company_profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.hhru.databinding.FragmentProfileBinding; // Здесь мы используем ваш правильный класс привязки

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding; // Используем правильный класс привязки
    private ProfileViewModel profileViewModel; // Для получения данных пользователя

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Инициализация ViewModel
        ProfileViewModel profileViewModel = new ViewModelProvider(this, new ProfileViewModelFactory(requireContext()))
                .get(ProfileViewModel.class);
        // Используем правильный метод для привязки View
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Получаем ссылку на TextView для отображения email
        final TextView emailTextView = binding.profileEmail; // Измените на актуальное имя поля, если нужно

        // Подписываемся на LiveData с email
        profileViewModel.getEmail().observe(getViewLifecycleOwner(), email -> {
            // Устанавливаем email в TextView
            String formattedEmail = "Ваш email: " + email;
            emailTextView.setText(formattedEmail);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Освобождаем binding
    }
}
