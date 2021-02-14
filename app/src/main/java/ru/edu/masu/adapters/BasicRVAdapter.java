package ru.edu.masu.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.edu.masu.R;

abstract public class BasicRVAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    protected List<T> items;
    private int layoutId;

    public BasicRVAdapter(List<T> items, int layoutId){
        this.items = items;
        this.layoutId = layoutId;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return createVH(view);
    }

    abstract VH createVH(View view);
    abstract void bind(T item, VH viewholder, int position);

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        bind(items.get(position), holder, position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}
