package ru.edu.masu.model.entities.quest;

import ru.edu.masu.model.entities.basic.DescriptionItem;
import ru.edu.masu.model.entities.basic.ImageInfo;

// подсказка к квесту - текст на фоне картинки
public class Hint extends DescriptionItem {

    // задержка перед показом подсказки
    private int delay;

    public Hint(ImageInfo info, String name, String hintText, int delay){
        super(info, name, hintText);
        setDelay(delay);
    }

    public Hint() {}

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }


}
