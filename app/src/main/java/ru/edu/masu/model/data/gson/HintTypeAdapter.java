package ru.edu.masu.model.data.gson;

import com.google.gson.stream.JsonReader;

import java.io.IOException;

import ru.edu.masu.model.entities.quest.Hint;

public class HintTypeAdapter extends DescriptionItemAdapter<Hint> {
    @Override
    public Hint create() {
        return new Hint();
    }

    @Override
    protected Hint readField(Hint item, String fieldName, JsonReader in) throws IOException {
        Hint hint = super.readField(item, fieldName, in);
        if(fieldName.equals("delay")){
            hint.setDelay(in.nextInt());
        }
        return hint;
    }
}
