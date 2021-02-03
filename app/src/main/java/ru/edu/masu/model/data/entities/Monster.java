package ru.edu.masu.model.data.entities;

public class Monster extends ImageItem{

    public boolean isMet() {
        return isMet;
    }

    public void setMet(boolean met) {
        isMet = met;
    }

    private boolean isMet;

    public Monster(String imgPath, String name, String desc) {
        super(imgPath, name, desc);
        this.isMet = false;
    }

    public Monster(){
        super("", "", "");
        this.isMet = false;
    }

}
