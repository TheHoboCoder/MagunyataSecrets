package ru.edu.masu.model;

public interface IQuestPassHolder {
     IQuestPass provideQuestPass();
     void check(IQuestPass questPass);
}
