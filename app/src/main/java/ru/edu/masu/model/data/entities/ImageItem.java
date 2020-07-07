package ru.edu.masu.model.data.entities;

public class ImageItem {

    private int image;
    private String name;
    private String desc;

    public ImageItem(int image, String name){
        this.image = image;
        this.name = name;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getImage() { return image; }
    public String getName() { return name; }
    public String getDescription() { return desc; }


}
