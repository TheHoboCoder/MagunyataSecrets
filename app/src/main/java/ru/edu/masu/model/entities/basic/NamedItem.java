package ru.edu.masu.model.entities.basic;

// элемент, у которого есть имя и визуальное представление(картинка)
public class NamedItem extends DisplayableItem implements INamedItem {

    private String name;

    public NamedItem(ImageInfo imageInfo, String name) {
        super(imageInfo);
        this.name = name;
    }

    public NamedItem(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
