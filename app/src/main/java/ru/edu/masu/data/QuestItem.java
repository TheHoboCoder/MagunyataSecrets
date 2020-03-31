package ru.edu.masu.data;

public class QuestItem {
    private int image;
    private String name;
    private String status;

    public QuestItem(int image, String name, String status) {
        this.image = image;
        this.name = name;
        this.status = status;
    }

    public int getImage() { return image; }
    public String getName() { return name; }
    public String getStatus() { return status; }
}

