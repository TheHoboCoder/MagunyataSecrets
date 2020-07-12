package ru.edu.masu.model;

import android.os.Parcel;

import java.util.ArrayList;
import java.util.List;

public class CodeQuestPass implements IQuestPass {

    private String passCode;
    private boolean isPassed;

    public enum PassType{
        TEXT,
        QR
    }

    public PassType getPassType() {
        return passType;
    }

    private PassType passType;

    public CodeQuestPass(String passCode, PassType passType){
        this.passCode = passCode;
        this.passType = passType;
    }

    public void enterCode(String code){
        isPassed = passCode.equals(code);
    }

    @Override
    public boolean isPassed() {
        return isPassed;
    }

    @Override
    public void from(IQuestPass questPass) {
        if(questPass instanceof CodeQuestPass){
            CodeQuestPass newInstance = (CodeQuestPass) questPass;
            if(newInstance.passCode.equals(this.passCode)
                && newInstance.getPassType() == passType){
                this.isPassed = newInstance.isPassed;
            }
        }
    }

    //Parcelable impl
    protected CodeQuestPass(Parcel in){
        isPassed = in.readByte() == 1;
        passCode = in.readString();
        int ordinalType = in.readInt();
        passType = PassType.values()[ordinalType];
    }

    public static final Creator<CodeQuestPass> CREATOR = new Creator<CodeQuestPass>() {
        @Override
        public CodeQuestPass createFromParcel(Parcel source) {
            return new CodeQuestPass(source);
        }

        @Override
        public CodeQuestPass[] newArray(int size) {
            return new CodeQuestPass[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (isPassed ? 1 : 0));
        dest.writeString(passCode);
        dest.writeInt(passType.ordinal());
    }
}
