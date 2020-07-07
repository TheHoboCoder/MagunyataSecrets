package ru.edu.masu.model.data.entities;

public class StoryItem {

    private int textImg;
    private int clipImg;

    public int getTextImg() {
        return textImg;
    }

    public StoryItem(int textImg, int clipImg) {
        this.textImg = textImg;
        this.clipImg = clipImg;
    }

    public void setTextImg(int textImg) {
        this.textImg = textImg;
    }

    public int getClipImg() {
        return clipImg;
    }

    public void setClipImg(int clipImg) {
        this.clipImg = clipImg;
    }


}
