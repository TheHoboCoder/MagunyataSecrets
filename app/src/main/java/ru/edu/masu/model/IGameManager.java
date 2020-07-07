package ru.edu.masu.model;

import java.util.List;

import ru.edu.masu.model.data.entities.Equipment;
import ru.edu.masu.model.data.entities.Monster;
import ru.edu.masu.model.data.entities.QuestItem;

public interface IGameManager {
    QuestItem getCurrentQuest();
    List<Monster> getMonstersInfo();
    List<Equipment> getEquipment();
}
