package ru.edu.masu.model;

import ru.edu.masu.model.IQuestFinished;

public interface IQuestPass {
    void addCallback(IQuestFinished iQuestFinished);
}
