package ru.edu.masu.model.entities.basic;

import ru.edu.masu.model.entities.basic.ImageInfo;
import ru.edu.masu.model.entities.basic.NamedItem;

// отображаемый элемент с именем и описанием
public class DescriptionItem extends NamedItem {

    public DescriptionItem(ImageInfo imageInfo, String name, String desc) {
        super(imageInfo, name);
        this.desc = desc;
    }

    private String desc;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public DescriptionItem(){
    }
}
