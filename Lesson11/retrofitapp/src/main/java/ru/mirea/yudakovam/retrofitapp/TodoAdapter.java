package ru.mirea.yudakovam.retrofitapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TodoAdapter extends RecyclerView.Adapter<TodoViewHolder> {
    private List<Todo> todos;
    private LayoutInflater layoutInflater;
    private ApiService apiService;

    public TodoAdapter(Context context, List<Todo> todoList, ApiService apiService) {
        this.todos = todoList;
        this.layoutInflater = LayoutInflater.from(context);
        this.apiService = apiService;
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item, parent, false);
        return new TodoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        Todo todo = todos.get(position);
        holder.textViewTitle.setText(todo.getTitle());
        holder.checkBoxCompleted.setChecked(todo.getCompleted());

        String gravatarHash = md5Hex(String.valueOf(todo.getUserId()));
        String gravatarUrl = "https://www.gravatar.com/avatar/" + gravatarHash + "?s=100&d=identicon";
        Picasso.get()
                .load(gravatarUrl)
                .placeholder(R.drawable.baseline_casino_24)
                .error(R.drawable.baseline_error_24)
                .resize(100, 100)
                .centerCrop()
                .into(holder.imageViewAvatar);

        String placeholderUrl = "https://via.placeholder.com/100x100.png?text=Image+" + todo.getId();
        Picasso.get()
                .load(placeholderUrl)
                .placeholder(R.drawable.baseline_casino_24)
                .error(R.drawable.baseline_error_24)
                .resize(100, 100)
                .centerCrop()
                .into(holder.imageViewExtra);

        holder.checkBoxCompleted.setOnCheckedChangeListener((buttonView, isChecked) -> {
            todo.setCompleted(isChecked);
            updateTodoOnServer(todo);
        });
    }

    private String md5Hex(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public int getItemCount() {
        return todos.size();
    }

    private void updateTodoOnServer(Todo todo) {
        Call<Todo> call = apiService.updateTodo(todo.getId(), todo);
        call.enqueue(new Callback<Todo>() {
            @Override
            public void onResponse(Call<Todo> call, Response<Todo> response) {
                if (response.isSuccessful()) {
                    Log.d("TodoAdapter", "Todo обновлено успешно: " + response.body());
                    Toast.makeText(layoutInflater.getContext(), "Задача обновлена успешно", Toast.LENGTH_SHORT).show();
                } else {
                    // Обработка ошибки при успешном ответе сервера, но с кодом ошибки
                    Log.e("TodoAdapter", "Ошибка обновления: " + response.code());
                    Toast.makeText(layoutInflater.getContext(), "Ошибка обновления: " + response.message(),
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Todo> call, Throwable t) {
                Log.e("TodoAdapter", "Не удалось обновить задачу: " + t.getMessage());
                Toast.makeText(layoutInflater.getContext(), "Ошибка подключения", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
