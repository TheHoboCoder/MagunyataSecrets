package ru.edu.masu.model.data.gson;

import ru.edu.masu.model.entities.quest.Monster;

public class MonsterItemAdapter extends DescriptionItemAdapter<Monster> {

    @Override
    public Monster create() {
        return new Monster();
    }
}
