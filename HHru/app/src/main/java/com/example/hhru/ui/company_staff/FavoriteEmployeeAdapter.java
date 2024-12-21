package com.example.hhru.ui.company_staff;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.domain.Models.Employee;
import com.example.hhru.R;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class FavoriteEmployeeAdapter extends RecyclerView.Adapter<FavoriteEmployeeAdapter.FavoriteEmployeeViewHolder> {
    private final List<Employee> employees;
    private final FavoriteEmployeeViewModel viewModel;
    private final Executor executor;

    public FavoriteEmployeeAdapter(List<Employee> employees, FavoriteEmployeeViewModel viewModel) {
        this.employees = employees;
        this.viewModel = viewModel;
        this.executor = Executors.newSingleThreadExecutor(); // Один Executor для всего адаптера
    }

    @NonNull
    @Override
    public FavoriteEmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_employee_favorite, parent, false);
        return new FavoriteEmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteEmployeeViewHolder holder, int position) {
        Employee employee = employees.get(position);
        holder.bind(employee);

        // Удаление сотрудника из избранного по нажатию на кнопку "Fired"
        holder.firedButton.setOnClickListener(v -> {
            Log.d("FavoriteEmployeeAdapter", "Удаление сотрудника: " + employee.getName());

            if (viewModel != null) {
                // Выполнение операции в фоновом потоке с использованием единого Executor
                executor.execute(() -> {
                    // Преобразуем строковый id в int и передаем в метод
                    int employeeId = Integer.parseInt(employee.getId());
                    viewModel.removeFavoriteEmployee(employeeId);
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return employees.size();
    }

    public void updateEmployees(List<Employee> newEmployees) {
        employees.clear();
        employees.addAll(newEmployees);
        notifyDataSetChanged();
    }

    static class FavoriteEmployeeViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameTextView;
        private final TextView positionTextView;
        private final ImageView avatarImageView;
        private final Button firedButton;

        public FavoriteEmployeeViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.name);
            positionTextView = itemView.findViewById(R.id.position);
            avatarImageView = itemView.findViewById(R.id.avatar);
            firedButton = itemView.findViewById(R.id.fired_button); // Инициализируем кнопку "Fired"
        }

        public void bind(Employee employee) {
            nameTextView.setText(employee.getName());
            positionTextView.setText(employee.getExpectedPosition());

            // Загрузка аватара с помощью Picasso
            Picasso.get()
                    .load(employee.getAvatar()) // URL аватара
                    .placeholder(R.drawable.ic_avatar_placeholder) // Изображение-заглушка
                    .into(avatarImageView); // Целевой ImageView
        }
    }
}
