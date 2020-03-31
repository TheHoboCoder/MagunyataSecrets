package ru.edu.masu.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ru.edu.masu.R;
import ru.edu.masu.data.QuestItem;

public class QuestItemRVMainAdapter extends RecyclerView.Adapter<QuestItemRVMainAdapter.QuestViewHolder> {

    ArrayList<QuestItem> arrayList;
    Context context;

    public QuestItemRVMainAdapter(ArrayList<QuestItem> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context   = context;
    }

    class QuestViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView image;
        public TextView name;
        public TextView status;

        public QuestViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            image  = itemView.findViewById(R.id.image);
            name   = itemView.findViewById(R.id.name);
            status = itemView.findViewById(R.id.status);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
        }
    }

    @NonNull
    @Override
    public QuestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quest_item, parent, false);
        QuestViewHolder vh = new QuestViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull QuestViewHolder holder, int position) {
        QuestItem questItem = arrayList.get(position);
        holder.image.setImageResource(questItem.getImage());
        holder.name.setText(questItem.getName());
        holder.status.setText(questItem.getStatus());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

}