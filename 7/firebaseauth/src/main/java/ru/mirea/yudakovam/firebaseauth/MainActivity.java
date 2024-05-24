package ru.mirea.yudakovam.firebaseauth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

import ru.mirea.yudakovam.firebaseauth.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity{
    private FirebaseAuth mAuth;
    private static final String TAG = "firebase";
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //точка входа для всех операций с фб
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {

        super.onStart();
        //не нуль если вошел в систему
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //если пользователь вошел, можно и юай обновить
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser user) {
        //обновляет уи на основе текущего состояния пользователя
        if (user != null) {
            //если не нуль, отображаем почту, айди, убираем кнопку войти
            binding.statusTextView.setText(getString(R.string.emailpassword_status_fmt,
                    user.getEmail(), user.isEmailVerified()));
            binding.detailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));
            binding.linearSignIn.setVisibility(View.GONE);
            binding.linearSignOut.setVisibility(View.VISIBLE);
            binding.verifyEmailButton.setEnabled(!user.isEmailVerified());
        } else {
            //выводим что вышли, кнопка войти видна, а выйти не видна
            binding.statusTextView.setText(R.string.signed_out);
            binding.detailTextView.setText(null);
            binding.linearSignIn.setVisibility(View.VISIBLE);
            binding.linearSignOut.setVisibility(View.GONE);

            //кнопка войти обработчик
            binding.signInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String email = binding.emailEditText.getText().toString();
                    String password = binding.passwordEditText.getText().toString();
                    if (!email.isEmpty() && !password.isEmpty())
                    {
                        //если не пустой имейл пароль
                        signIn(email, password);
                    } else {
                        Toast.makeText(MainActivity.this, "Заполните все поля!",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });

            //выйти обработчик
            binding.signOutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    signOut();
                }
            });

            //создать аккаунт обработчик
            binding.createAccountButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String email = binding.emailEditText.getText().toString();
                    String password = binding.passwordEditText.getText().toString();
                    if (!email.isEmpty() && !password.isEmpty())
                    {
                        createAccount(email, password);
                    } else {
                        Toast.makeText(MainActivity.this, "Заполните все поля!",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });

            //верификация обработчик
            binding.verifyEmailButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendEmailVerification();
                }
            });
        }
    }

    //создать аккаунт функция
    private void createAccount(String email, String password) {
        Log.d(TAG, "create account: " + email);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //если успешны, выводим что молодцы и обновляем уи
                            Log.d(TAG, "createUserWithEmailAndPassword: success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            //иначе выводим сообщение о провале и уи обновляем на нуль
                            Log.d(TAG, "createUserWithEmailAndPassword: failure");
                            Toast.makeText(MainActivity.this, "Authentication failed",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    //войти функция
    private void signIn(String email, String password) {
        Log.d(TAG, "signIn: " + email);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //вошли - молодцы, обновляем интерфейс
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail: success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            //не  вощли - тоже обновляем интерфейс
                            Log.d(TAG, "signInWithEmail: failure");
                            Toast.makeText(MainActivity.this, "Authentication failed",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        if (!task.isSuccessful()) {
                            binding.statusTextView.setText(R.string.auth_failed);
                        }
                    }
                });
    }

    //выход функция
    private void signOut() {
        mAuth.signOut();
        updateUI(null);
    }

    //верификация функция
    private void sendEmailVerification() {
        //вырубаем кнопку
        binding.verifyEmailButton.setEnabled(false);
        final FirebaseUser user = mAuth.getCurrentUser();
        //проверяем что юзер не нуль
        Objects.requireNonNull(user).sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        binding.verifyEmailButton.setEnabled(true);

                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this,
                                    "Verification email sent to " + user.getEmail(), Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d(TAG, "sendEmailVerification", task.getException());
                            Toast.makeText(MainActivity.this,
                                    "Failed to send verification email.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}