package ru.edu.masu.model.data.entities;

public class Hint {

    private int delay;
    private int hintImage;
    private String hintText;

    public Hint(String hintText, int hintImage, int delay){
        setHintImage(hintImage);
        setHintText(hintText);
        setDelay(delay);
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public int getHintImage() {
        return hintImage;
    }

    public void setHintImage(int hintImage) {
        this.hintImage = hintImage;
    }

    public String getHintText() {
        return hintText;
    }

    public void setHintText(String hintText) {
        this.hintText = hintText;
    }



}
