package ru.mirea.yudakovam.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.snackbar.Snackbar;

public class MyDateDialogFragment extends DialogFragment {

    private int myYear, myMonth, myDay;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Используем текущую дату в качестве значения по умолчанию
        java.util.Calendar c = java.util.Calendar.getInstance();
        myYear = c.get(java.util.Calendar.YEAR);
        myMonth = c.get(java.util.Calendar.MONTH);
        myDay = c.get(java.util.Calendar.DAY_OF_MONTH);

        // Создаем и возвращаем DatePickerDialog
        return new DatePickerDialog(getActivity(), myCallBack, myYear, myMonth, myDay);
    }

    DatePickerDialog.OnDateSetListener myCallBack = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myYear = year;
            myMonth = month;
            myDay = dayOfMonth;

            String message = "Выбранная дата: " + myDay + "/" + (myMonth + 1) + "/" + myYear;

            View rootView = getActivity().getWindow().getDecorView().findViewById(android.R.id.content);

            Snackbar.make(rootView, message, Snackbar.LENGTH_LONG).show();
        }
    };
}
