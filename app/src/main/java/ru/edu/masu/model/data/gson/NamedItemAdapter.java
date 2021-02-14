package ru.edu.masu.model.data.gson;

import com.google.gson.stream.JsonReader;

import java.io.IOException;

import ru.edu.masu.model.entities.basic.INamedItem;

public abstract class NamedItemAdapter<T extends INamedItem> extends DisplayableItemAdapter<T> {
    @Override
    protected T readField(T item, String fieldName, JsonReader in) throws IOException {
        T namedItem = super.readField(item, fieldName, in);
        if(fieldName.equals("name")){
            namedItem.setName(in.nextString());
        }
        return namedItem;
    }
}
