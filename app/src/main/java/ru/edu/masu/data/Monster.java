package ru.edu.masu.data;

public class Monster {
    //TODO:сеттеры/геттеры
    public int picId;
    public String monsterDesc;
    public String monsterName;

    public Monster(int picId,String monsterName,String monsterDesc) {
        this.picId = picId;
        this.monsterDesc = monsterDesc;
        this.monsterName = monsterName;
    }
}
