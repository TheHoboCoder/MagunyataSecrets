package ru.edu.masu.data;

import ru.edu.masu.model.IQuestFinished;

public interface IQuestPass {
    void addCallback(IQuestFinished iQuestFinished);
}
