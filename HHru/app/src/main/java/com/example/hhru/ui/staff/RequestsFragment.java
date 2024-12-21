package com.example.hhru.ui.staff;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.domain.Models.Employee;
import com.example.hhru.R;
import com.example.data.AppDatabase;
import com.example.data.FavoriteEmployeeRepositoryImpl;
import com.example.domain.Repositories.FavoriteEmployeeRepository;

import java.util.ArrayList;
import java.util.List;

public class RequestsFragment extends Fragment {
    private RecyclerView recyclerView;
    private EmployeeAdapter employeeAdapter;
    private EmployeeViewModel employeeViewModel;

    public static RequestsFragment newInstance() {
        return new RequestsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_requests, container, false);

        recyclerView = view.findViewById(R.id.requests_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Инициализация базы данных и репозитория
        AppDatabase database = AppDatabase.getInstance(requireContext());
        FavoriteEmployeeRepository favoriteEmployeeRepository =
                new FavoriteEmployeeRepositoryImpl(database.employeeDao());

        // Инициализация ViewModel через фабрику
        EmployeeViewModelFactory factory = new EmployeeViewModelFactory(requireActivity().getApplication(), favoriteEmployeeRepository);
        employeeViewModel = new ViewModelProvider(this, factory).get(EmployeeViewModel.class);

        // Инициализация адаптера с ViewModel
        employeeAdapter = new EmployeeAdapter(new ArrayList<>(), employeeViewModel);
        recyclerView.setAdapter(employeeAdapter);

        // Наблюдаем за изменениями списка сотрудников
        employeeViewModel.getEmployees().observe(getViewLifecycleOwner(), new Observer<List<Employee>>() {
            @Override
            public void onChanged(List<Employee> employees) {
                if (employees != null) {
                    Log.d("RequestsFragment", "Loaded " + employees.size() + " employees");
                    employeeAdapter.updateEmployees(employees);
                }
            }
        });

        // Загружаем сотрудников
        employeeViewModel.loadEmployees();

        return view;
    }
}
