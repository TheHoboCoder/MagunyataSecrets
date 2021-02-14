package ru.edu.masu.model.entities.questPass;

// базовая реализация интерфейса IQuestPass
abstract public class BaseQuestPass implements IQuestPass {

    protected boolean isPassed = false;
    private String name;

    @Override
    public boolean isPassed() {
        return isPassed;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}
