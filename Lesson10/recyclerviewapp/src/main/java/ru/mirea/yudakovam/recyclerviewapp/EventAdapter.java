package ru.mirea.yudakovam.recyclerviewapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventViewHolder> {

    private List<Event> events;

    public EventAdapter(List<Event> events) {
        this.events = events;
    }

    @NonNull
    @Override
    //вызывается, когда RecyclerView нуждается в новом
    //ViewHolder. Внутри данного метода создается новый объект ViewHolder на основе
    //макета элемента списка (item_layout.xml)
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_item, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    //вызывается для каждого элемента списка, который
    //становится видимым. Здесь происходит привязка данных к ViewHolder (например,
    //установка текста и изображения).
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event event = events.get(position);
        holder.textViewTitle.setText(event.getTitle());
        holder.textViewDescription.setText(event.getDescription());
        holder.imageViewEvent.setImageResource(event.getImageResource());
    }

    @Override
    //возвращает общее количество элементов в списке.
    public int getItemCount() {
        return events.size();
    }
}
