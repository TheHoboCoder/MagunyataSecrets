package ru.edu.masu.model;

import android.os.Parcelable;

public interface IQuestPass extends Parcelable {
    boolean isPassed();
    void from(IQuestPass questPass);
}
