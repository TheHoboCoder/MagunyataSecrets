package ru.edu.masu.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.edu.masu.R;
import ru.edu.masu.model.entities.quest.StoryItem;
import ru.edu.masu.utils.ImageCaching;

public class StoryItemAdapter extends BasicRVAdapter<StoryItem, StoryItemAdapter.StoryItemVH>{

    OnScrollListener scrollListener;

    public StoryItemAdapter(List<StoryItem> items, OnScrollListener scrollListener) {
        super(items, R.layout.item_story);
        this.scrollListener = scrollListener;
    }

    public interface OnScrollListener{
        public void scrollTo(int pos);
    }

    @Override
    StoryItemVH createVH(View view) {
        return new StoryItemVH(view);
    }

    @Override
    void bind(StoryItem item, StoryItemVH viewholder, int position) {
        viewholder.bind(item, position);
    }

    public class StoryItemVH extends RecyclerView.ViewHolder {
        ImageView img, forwardBtn, backwardBtn;
        TextView descTxt;

        public StoryItemVH(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            forwardBtn = itemView.findViewById(R.id.forwardBtn);
            backwardBtn = itemView.findViewById(R.id.backwardBtn);
            descTxt = itemView.findViewById(R.id.desc);
        }

        public void bind(StoryItem item, int position){
            ImageCaching.loadIn(itemView.getContext(), item.getImageInfo().getImgPath(), img);
            descTxt.setText(item.getCaption());
            if(position > 0){
                backwardBtn.setVisibility(View.VISIBLE);
            }
            else{
                backwardBtn.setVisibility(View.INVISIBLE);
            }

            backwardBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    scrollListener.scrollTo(position-1);
                }
            });

            forwardBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    scrollListener.scrollTo(position+1);
                }
            });

        }
    }
}
