package ru.mirea.yudakovam.securesharedpreferences;

import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;

import java.io.IOException;
import java.security.GeneralSecurityException;

import ru.mirea.yudakovam.securesharedpreferences.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding	=	ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        //создание специикации генерации ключа шифрования по алгоритму
        KeyGenParameterSpec keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC;
        try {
            //создание публичного ключа
            String mainKey = MasterKeys.getOrCreate(keyGenParameterSpec);
            SharedPreferences securePreferences = EncryptedSharedPreferences.create(
                    //имя файла ностроек
                    "secret_shared_pref",

                    mainKey,
                    getBaseContext(),
                    //шифрование ключей и их значений
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
            //запись через securePreferences
            securePreferences.edit().putString("NAME", "Чехов А.П.").apply();
            //дефолтное значение
            binding.textPoet.setText(securePreferences.getString("NAME", ""));
            //картинка поэта
            binding.imagePoet.setImageResource(R.drawable.poet);
        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }
}