package ru.edu.masu.model;

public interface IQuestPass {
    void addCallback(IQuestFinished iQuestFinished);
    void removeAllCallbacks();
}
