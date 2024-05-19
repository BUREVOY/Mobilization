package ru.mirea.yudakovam.thread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Arrays;

import ru.mirea.yudakovam.thread.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private	int	counter	=	0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        binding	=	ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        TextView infoTextView = binding.textView;
        Thread mainThread = Thread.currentThread();
        infoTextView.setText("Имя текущего потока: " + mainThread.getName());
// Меняем имя и выводим в текстовом поле
        mainThread.setName("МОЙ НОМЕР ГРУППЫ: 11, НОМЕР ПО СПИСКУ: 30, МОЙ ЛЮБИИМЫЙ ФИЛЬМ: Барби");
        infoTextView.append("\n Новое имя потока: " + mainThread.getName());
        Log.d(MainActivity.class.getSimpleName(),	"Stack:	"	+	Arrays.toString(mainThread.getStackTrace()));
        Log.d(MainActivity.class.getSimpleName(),	"Group:	"	+	mainThread.getThreadGroup());
        binding.buttonMirea.setOnClickListener(new	View.OnClickListener()	{
            @Override
            public	void	onClick(View	v)	{
                calculateAveragePairs();
                new	Thread(new	Runnable()	{
                    public	void run()	{
                        int	numberThread	=	counter++;
                        Log.d("ThreadProject",	String.format("Запущен	поток	№	%d	студентом	группы	№	%s	номер	по списку	№	%d	",	numberThread,	"БСБО-11-21",	-1));
                        long	endTime	=	System.currentTimeMillis()	+	20	*	1000;
                        while	(System.currentTimeMillis()	<	endTime)	{
                            synchronized	(this)	{
                                try	{
                                    wait(endTime	- System.currentTimeMillis());
                                    Log.d(MainActivity.class.getSimpleName(),	"Endtime:	"	+	endTime);
                                }	catch	(Exception	e)	{
                                    throw	new	RuntimeException(e);
                                }
                            }
                            Log.d("ThreadProject",	"Выполнен поток №	"	+	numberThread);
                        }
                    }
                }).start();
            }
        });

    }

    private void calculateAveragePairs() {
        int totalPairs = Integer.parseInt(binding.editTextTotalPairs.getText().toString());
        int totalDays = Integer.parseInt(binding.editTextTotalDays.getText().toString());

        // Запускаем вычисление в фоновом потоке
        new Thread(new Runnable() {
            @Override
            public void run() {
                final double result = (double) totalPairs / totalDays;
                // Передаем результат на главный поток для обновления UI
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        // Добавляем новую строку к существующему тексту
                        binding.textView.append("\n");
                        binding.textView.append(String.format("Среднее количество пар в день за месяц: %.2f", result));
                    }
                });
            }
        }).start();
    }
}