package ru.edu.masu.model.data.gson;

import com.google.gson.stream.JsonReader;

import java.io.IOException;

import ru.edu.masu.model.entities.basic.DescriptionItem;

abstract public class DescriptionItemAdapter<T extends DescriptionItem> extends NamedItemAdapter<T> {
    @Override
    protected T readField(T item, String fieldName, JsonReader in) throws IOException {
        T descriptionItem = super.readField(item, fieldName, in);
        if(fieldName.equals("desc")){
            descriptionItem.setDesc(in.nextString());
        }
        return descriptionItem;
    }
}
