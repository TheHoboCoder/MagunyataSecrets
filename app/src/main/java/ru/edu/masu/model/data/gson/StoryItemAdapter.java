package ru.edu.masu.model.data.gson;

import com.google.gson.stream.JsonReader;

import java.io.IOException;

import ru.edu.masu.model.entities.quest.StoryItem;

public class StoryItemAdapter extends DisplayableItemAdapter<StoryItem>{
    @Override
    public StoryItem create() {
        return new StoryItem();
    }

    @Override
    protected StoryItem readField(StoryItem item, String fieldName, JsonReader in) throws IOException {
        StoryItem storyItem = super.readField(item, fieldName, in);
        if(fieldName.equals("caption")){
            storyItem.setCaption(in.nextString());
        }
        return storyItem;
    }
}
