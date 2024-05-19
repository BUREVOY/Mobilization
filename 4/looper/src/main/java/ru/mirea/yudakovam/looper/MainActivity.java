package ru.mirea.yudakovam.looper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;

import ru.mirea.yudakovam.looper.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding	=	ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        Создание обработчика данных (1)
        Handler	mainThreadHandler	=	new	Handler(Looper.getMainLooper())	{
            @Override
            public	void	handleMessage(Message msg)	{
                Log.d(MainActivity.class.getSimpleName(),	"Task	execute.	This	is	result:	"	+	msg.getData().getString("result"));
            }
        };
//        Создание и запуск потока (2)
        MyLooper	myLooper	=	new	MyLooper(mainThreadHandler);
        myLooper.start();

        binding.editTextMirea.setText("Мой номер по списку №30");
        binding.buttonMirea.setOnClickListener(new	View.OnClickListener()	{
            @Override
            public	void	onClick(View v) {
                // Получение возраста из текстового поля
                int age = Integer.parseInt(binding.editTextAge.getText().toString());
                // Получение профессии из текстового поля
                String occupation = binding.editTextOccupation.getText().toString();

                // Создание сообщения с возрастом и профессией
                Message msg = Message.obtain();
                Bundle bundle = new Bundle();
                bundle.putInt("age", age);
                bundle.putString("occupation", occupation);
                msg.setData(bundle);

                bundle.putString("KEY",	"mirea");
                myLooper.mHandler.sendMessage(msg);
            }
        });
    }
}