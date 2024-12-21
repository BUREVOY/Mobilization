package com.example.data;

import com.example.domain.Repositories.AuthRepository;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class FirebaseAuthRepository implements AuthRepository {
    private final FirebaseAuth firebaseAuth;

    public FirebaseAuthRepository() {
        this.firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void login(String email, String password, AuthCallback callback) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> handleTask(task, callback));
    }

    @Override
    public void register(String email, String password, AuthCallback callback) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> handleTask(task, callback));
    }

    private void handleTask(Task<AuthResult> task, AuthCallback callback) {
        if (task.isSuccessful()) {
            callback.onSuccess();
        } else {
            callback.onFailure(task.getException().getMessage());
        }
    }
}

