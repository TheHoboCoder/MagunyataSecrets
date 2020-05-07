package ru.edu.masu.data;

public class QuestItem {
    private int image;
    private String name;
    private String status;
    private int background;

    public QuestItem(int image, String name, String status,int background) {
        this.image = image;
        this.name = name;
        this.status = status;
        this.background = background;
    }

    public int getImage() { return image; }
    public String getName() { return name; }
    public String getStatus() { return status; }

    public int getBackground() {return background; }
}

