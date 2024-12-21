package ru.mirea.yudakovam.retrofitapp;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TodoViewHolder extends RecyclerView.ViewHolder {
    TextView textViewTitle;
    CheckBox checkBoxCompleted;
    ImageView imageViewAvatar;
    ImageView imageViewExtra;

    public TodoViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewTitle = itemView.findViewById(R.id.textViewTitle);
        checkBoxCompleted = itemView.findViewById(R.id.checkBoxCompleted);
        imageViewAvatar = itemView.findViewById(R.id.imageViewAvatar);
        imageViewExtra = itemView.findViewById(R.id.imageViewExtra);
    }
}
