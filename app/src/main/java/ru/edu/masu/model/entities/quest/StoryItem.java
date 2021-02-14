package ru.edu.masu.model.entities.quest;

import ru.edu.masu.model.entities.basic.DisplayableItem;
import ru.edu.masu.model.entities.basic.ImageInfo;

// у каждого квеста есть предыстория в виде комикса
// данный класс описывает кадр этого комикса - он содержит картинку и подпись к ней
public class StoryItem extends DisplayableItem {

    private String caption;

    public StoryItem(ImageInfo imageInfo, String caption) {
        super(imageInfo);
        this.caption = caption;
    }

    public StoryItem(){
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
}
