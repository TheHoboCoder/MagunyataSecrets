package ru.edu.masu.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.edu.masu.R;
import ru.edu.masu.model.entities.quest.QuestItem;
import ru.edu.masu.utils.ImageCaching;

public class QuestItemRVMainAdapter extends RecyclerView.Adapter<QuestItemRVMainAdapter.QuestViewHolder> {

    List<QuestItem> arrayList;
    Context context;

    public QuestItemRVMainAdapter(List<QuestItem> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context   = context;
    }

    public class QuestViewHolder extends RecyclerView.ViewHolder{

        public ImageView image;
        public TextView name;
        public TextView status;
        public ConstraintLayout quest_item_layout;

        public QuestViewHolder(@NonNull View itemView) {
            super(itemView);
            image  = itemView.findViewById(R.id.image);
            name   = itemView.findViewById(R.id.name);
            status = itemView.findViewById(R.id.status);
            quest_item_layout = itemView.findViewById(R.id.quest_item_layout);
        }
    }

    @NonNull
    @Override
    public QuestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quest, parent, false);
        return new QuestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestViewHolder holder, int position) {
        QuestItem questItem = arrayList.get(position);
        int background = -1;
        String status = "";
        Context context = holder.itemView.getContext();
        switch(questItem.getStatus()){
            case LOCKED:
                background = R.drawable.bg3;
                status = context.getString(R.string.quest_status_locked);
                break;
            case ACTIVE:
                background = R.drawable.bg2;
                status = context.getString(R.string.quest_status_active);
                break;
            case FINISHED:
                //TODO: add quest finished background
                background = R.drawable.bg3;
                status = context.getString(R.string.quest_status_finished);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + questItem.getStatus());
        }
        holder.quest_item_layout.setBackgroundResource(background);
        ImageCaching.loadIn(context, questItem.getImageInfo().getImgPath(), holder.image);
        holder.name.setText(questItem.getName());
        holder.status.setText(status);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

}