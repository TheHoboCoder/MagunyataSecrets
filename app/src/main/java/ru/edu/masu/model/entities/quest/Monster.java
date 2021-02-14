package ru.edu.masu.model.entities.quest;

import ru.edu.masu.model.entities.basic.DescriptionItem;
import ru.edu.masu.model.entities.basic.ImageInfo;

public class Monster extends DescriptionItem {

    public boolean isMet() {
        return isMet;
    }

    public void setMet(boolean met) {
        isMet = met;
    }

    private boolean isMet;

    public Monster(String imgPath, String name, String desc) {
        super(new ImageInfo(imgPath), name, desc);
        this.isMet = false;
    }

    public Monster(){
        this.isMet = false;
    }

}
