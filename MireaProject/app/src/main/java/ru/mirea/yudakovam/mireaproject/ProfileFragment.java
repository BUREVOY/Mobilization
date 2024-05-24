package ru.mirea.yudakovam.mireaproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.mirea.yudakovam.mireaproject.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {
    private EditText editTextName;
    private FragmentProfileBinding fragmentProfileBinding;
    private EditText editTextAge;
    private Button buttonSave;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentProfileBinding = FragmentProfileBinding.inflate(inflater, container, false);
        View view = fragmentProfileBinding.getRoot();

        editTextName = fragmentProfileBinding.editTextName;
        editTextAge = fragmentProfileBinding.editTextAge;
        buttonSave = fragmentProfileBinding.buttonSave;

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("profile", Context.MODE_PRIVATE);

        String savedName = sharedPreferences.getString("name", "");
        int savedAge = sharedPreferences.getInt("age", 0);
        editTextName.setText(savedName);
        editTextAge.setText(String.valueOf(savedAge));

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit(); // переместили объявление сюда

                String name = editTextName.getText().toString();
                int age = Integer.parseInt(editTextAge.getText().toString());

                editor.putString("name", name);
                editor.putInt("age", age);
                editor.apply();
            }
        });

        return view;
    }
}

