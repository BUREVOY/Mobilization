package com.example.hhru.ui.company_staff;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.data.AppDatabase;
import com.example.data.FavoriteEmployeeRepositoryImpl;
import com.example.data.EmployeeDao;
import com.example.domain.Repositories.FavoriteEmployeeRepository;
import com.example.hhru.R;

import java.util.ArrayList;

public class FavoriteEmployeeFragment extends Fragment {
    private RecyclerView favoriteEmployeeRecyclerView;
    private FavoriteEmployeeAdapter favoriteEmployeeAdapter; // Используем правильный адаптер
    private FavoriteEmployeeRepository favoriteEmployeeRepository;
    private FavoriteEmployeeViewModel favoriteEmployeeViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Создаем разметку для фрагмента
        View view = inflater.inflate(R.layout.fragment_favorite_employee, container, false);

        // Инициализация DAO
        EmployeeDao employeeDao = AppDatabase.getInstance(requireContext()).employeeDao();

        // Инициализация репозитория
        favoriteEmployeeRepository = new FavoriteEmployeeRepositoryImpl(employeeDao);

        // Инициализация ViewModel
        favoriteEmployeeViewModel = new ViewModelProvider(this,
                new FavoriteEmployeeViewModelFactory(favoriteEmployeeRepository)).get(FavoriteEmployeeViewModel.class);

        // Инициализация RecyclerView
        favoriteEmployeeRecyclerView = view.findViewById(R.id.favorite_employee_recycler_view);
        favoriteEmployeeRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Инициализация адаптера с пустым списком FavoriteEmployee
        favoriteEmployeeAdapter = new FavoriteEmployeeAdapter(new ArrayList<>(), favoriteEmployeeViewModel);
        favoriteEmployeeRecyclerView.setAdapter(favoriteEmployeeAdapter);

        // Подписка на LiveData для обновления данных
        favoriteEmployeeViewModel.getFavoriteEmployees().observe(getViewLifecycleOwner(), employees -> {
            if (employees != null && !employees.isEmpty()) {
                // Обновляем данные в адаптере
                favoriteEmployeeAdapter.updateEmployees(employees);
            } else {
                Log.d("FavoriteEmployeeFragment", "No favorite employees found");
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        favoriteEmployeeViewModel.loadFavoriteEmployees();  // Загрузка избранных сотрудников
    }


    public static FavoriteEmployeeFragment newInstance() {
        return new FavoriteEmployeeFragment();
    }
}
