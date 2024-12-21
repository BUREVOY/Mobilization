package com.example.hhru.ui.staff;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.domain.Models.Employee;
import com.example.domain.Models.FavoriteEmployee;
import com.example.data.Models.EmployeeEntity;
import com.example.hhru.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {
    private List<Employee> employees;
    private EmployeeViewModel employeeViewModel;
    private static final String TAG = "EmployeeAdapter";

    public EmployeeAdapter(List<Employee> employees, EmployeeViewModel employeeViewModel) {
        this.employees = employees;
        this.employeeViewModel = employeeViewModel;
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_employee_request, parent, false);
        return new EmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        Employee employee = employees.get(position);
        holder.employeeName.setText(employee.getName());
        holder.employeeExperience.setText("Experience: " + employee.getExperience() + " years");
        holder.expectedPosition.setText("Expected Position: " + employee.getExpectedPosition());
        Picasso.get().load(employee.getAvatar()).into(holder.employeeImage);

        holder.favoriteButton.setOnClickListener(v -> {
            Log.d(TAG, "Favorite button clicked for employee: " + employee.getName());

            // Преобразуем Employee в FavoriteEmployee
            FavoriteEmployee favoriteEmployee = new FavoriteEmployee(
                    employee.getName(),
                    employee.getAvatar(),
                    employee.getExperience(),
                    employee.getExpectedPosition(),  // Ожидаемая должность
                    employee.getId()  // Идентификатор сотрудника
            );

            // Наблюдаем за состоянием избранного
            employeeViewModel.isFavorite(employee.getId()).observe((LifecycleOwner) holder.itemView.getContext(), isFavorite -> {
                if (isFavorite) {
                    Log.d(TAG, "Removing employee from favorites: " + employee.getName());
                    employeeViewModel.deleteFavoriteEmployee(employee.getId()); // Удаляем из избранного
                } else {
                    Log.d("EmployeeRepository", "Attempting to insert employee: " + employee.getId());
                    employeeViewModel.addFavoriteEmployee(favoriteEmployee); // Добавляем в избранное
                }
            });
        });
    }

    @Override
    public int getItemCount() {
        return employees != null ? employees.size() : 0;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
        notifyDataSetChanged();
    }

    // Обновляем данные сотрудников, передавая список Employee
    public void updateEmployees(List<Employee> newEmployees) {
        this.employees = newEmployees;
        notifyDataSetChanged();
    }

    public static class EmployeeViewHolder extends RecyclerView.ViewHolder {
        ImageView employeeImage;
        TextView employeeName;
        TextView employeeExperience;
        TextView expectedPosition;  // Поле для отображения ожидаемой должности
        Button favoriteButton;

        public EmployeeViewHolder(@NonNull View itemView) {
            super(itemView);
            employeeImage = itemView.findViewById(R.id.avatar);
            employeeName = itemView.findViewById(R.id.name);
            employeeExperience = itemView.findViewById(R.id.experience);
            expectedPosition = itemView.findViewById(R.id.expected_position);  // Инициализируем новое поле
            favoriteButton = itemView.findViewById(R.id.add_to_team_button);
        }
    }
}
