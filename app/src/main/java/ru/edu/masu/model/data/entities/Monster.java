package ru.edu.masu.model.data.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class Monster implements Parcelable {
    private int monsterId;
    private int picId;
    private String monsterDesc;
    private String monsterName;

    private int monsterDescId;
    private int monsterNameId;
    private boolean isMet;

    public Monster(int picId,String monsterName,String monsterDesc) {
        this.picId = picId;
        this.monsterDesc = monsterDesc;
        this.monsterName = monsterName;
    }

    //TODO: временный конструктор, чтобы не использовать context внутри репозитория
    //на время пока объекты создаются в коде. В дальнейшем нужно реализовать загрузку из JSON/базы
    public Monster(int id, int picId,int monsterNameId,int monsterDescId) {
        this.monsterId = id;
        this.picId = picId;
        this.monsterDescId = monsterDescId;
        this.monsterNameId = monsterNameId;
    }

    public int getMonsterId() {
        return monsterId;
    }

    public void setMonsterId(int monsterId) {
        this.monsterId = monsterId;
    }

    public int getPicId() {
        return picId;
    }

    public void setPicId(int picId) {
        this.picId = picId;
    }

    public String getMonsterDesc() {
        return monsterDesc;
    }

    public void setMonsterDesc(String monsterDesc) {
        this.monsterDesc = monsterDesc;
    }

    public String getMonsterName() {
        return monsterName;
    }

    public void setMonsterName(String monsterName) {
        this.monsterName = monsterName;
    }

    public int getMonsterDescId() {
        return monsterDescId;
    }

    public void setMonsterDescId(int monsterDescId) {
        this.monsterDescId = monsterDescId;
    }

    public int getMonsterNameId() {
        return monsterNameId;
    }

    public void setMonsterNameId(int monsterNameId) {
        this.monsterNameId = monsterNameId;
    }

    public boolean isMet() {
        return isMet;
    }

    public void setMet(boolean met) {
        isMet = met;
    }

    protected Monster(Parcel in) {
        picId = in.readInt();
        monsterDesc = in.readString();
        monsterName = in.readString();
        monsterDescId = in.readInt();
        monsterNameId = in.readInt();
        isMet = in.readByte() != 0;
    }

    public static final Creator<Monster> CREATOR = new Creator<Monster>() {
        @Override
        public Monster createFromParcel(Parcel in) {
            return new Monster(in);
        }

        @Override
        public Monster[] newArray(int size) {
            return new Monster[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(picId);
        dest.writeString(monsterDesc);
        dest.writeString(monsterName);
        dest.writeInt(monsterDescId);
        dest.writeInt(monsterNameId);
        dest.writeByte((byte) (isMet ? 1 : 0));
    }
}
