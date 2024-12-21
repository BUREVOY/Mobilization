package com.example.hhru;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.data.FirebaseAuthRepository;
import com.example.domain.Repositories.AuthRepository;
import com.example.domain.Usecases.LoginUser;
import com.example.domain.Usecases.RegisterUser;

public class AuthActivity extends AppCompatActivity {

    private EditText emailLoginEditText, passwordLoginEditText, emailRegisterEditText, passwordRegisterEditText, confirmPasswordRegisterEditText;
    private Button loginButton, registerButton;
    private TextView authOrRegisterText, switchToRegisterButton, switchToLoginButton;
    private AuthViewModel authViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        // Инициализация элементов интерфейса
        emailLoginEditText = findViewById(R.id.email_login);
        passwordLoginEditText = findViewById(R.id.password_login);
        emailRegisterEditText = findViewById(R.id.email_register);
        passwordRegisterEditText = findViewById(R.id.password_register);
        confirmPasswordRegisterEditText = findViewById(R.id.confirm_password_register);
        loginButton = findViewById(R.id.login_button);
        registerButton = findViewById(R.id.register_button);
        authOrRegisterText = findViewById(R.id.auth_or_register_text);
        switchToRegisterButton = findViewById(R.id.switch_to_register);
        switchToLoginButton = findViewById(R.id.switch_to_login);

        // Инициализация репозитория
        AuthRepository authRepository = new FirebaseAuthRepository();  // Поменяйте на ваш репозиторий

        // Инициализация UseCases
        LoginUser loginUser = new LoginUser(authRepository);
        RegisterUser registerUser = new RegisterUser(authRepository);

        // Инициализация ViewModel
        authViewModel = new ViewModelProvider(this, new AuthViewModelFactory(loginUser, registerUser)).get(AuthViewModel.class);

        // Наблюдение за состоянием успешного логина
        authViewModel.getLoginSuccess().observe(this, isSuccess -> {
            if (isSuccess) {
                String email = emailLoginEditText.getText().toString();
                saveUserData(email);  // Сохранение данных пользователя
                Toast.makeText(AuthActivity.this, "Login successful", Toast.LENGTH_SHORT).show();

                // Перенаправление в MainActivity или другую активность
                startActivity(new Intent(AuthActivity.this, MainActivity.class));
                finish();  // Закрытие текущей активности
            }
        });

        // Наблюдение за сообщением об ошибке
        authViewModel.getErrorMessage().observe(this, errorMessage -> {
            if (errorMessage != null) {
                Toast.makeText(AuthActivity.this, "Login failed: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

        // Логика для авторизации
        loginButton.setOnClickListener(v -> {
            String email = emailLoginEditText.getText().toString();
            String password = passwordLoginEditText.getText().toString();

            // Проверка на пустые поля и валидация
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(AuthActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!isValidEmail(email)) {
                Toast.makeText(AuthActivity.this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
                return;
            }

            // Выполнение логина через ViewModel
            authViewModel.login(email, password);
        });

        // Логика для регистрации
        registerButton.setOnClickListener(v -> {
            String email = emailRegisterEditText.getText().toString();
            String password = passwordRegisterEditText.getText().toString();
            String confirmPassword = confirmPasswordRegisterEditText.getText().toString();

            // Проверка на пустые поля и валидация
            if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(AuthActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!isValidEmail(email)) {
                Toast.makeText(AuthActivity.this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(confirmPassword)) {
                Toast.makeText(AuthActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            // Выполнение регистрации через ViewModel
            authViewModel.register(email, password);
        });

        // Переключение на форму регистрации
        switchToRegisterButton.setOnClickListener(v -> switchToRegister());

        // Переключение на форму авторизации
        switchToLoginButton.setOnClickListener(v -> switchToLogin());

        // Изначально показываем форму авторизации
        showLoginForm();
    }

    private void switchToRegister() {
        findViewById(R.id.login_form).setVisibility(View.GONE);
        findViewById(R.id.register_form).setVisibility(View.VISIBLE);
        authOrRegisterText.setText("Register");
    }

    private void switchToLogin() {
        findViewById(R.id.register_form).setVisibility(View.GONE);
        findViewById(R.id.login_form).setVisibility(View.VISIBLE);
        authOrRegisterText.setText("Login");
    }

    private void showLoginForm() {
        findViewById(R.id.login_form).setVisibility(View.VISIBLE);
        findViewById(R.id.register_form).setVisibility(View.GONE);
        authOrRegisterText.setText("Login");
    }

    private void saveUserData(String email) {
        // Сохраняем информацию о пользователе в SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", email);  // Сохраняем email
        editor.putBoolean("isLoggedIn", true);  // Устанавливаем флаг, что пользователь авторизован
        editor.apply();
    }

    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
