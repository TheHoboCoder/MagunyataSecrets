package ru.edu.masu.model;

import java.util.List;

import ru.edu.masu.data.Equipment;
import ru.edu.masu.data.Monster;
import ru.edu.masu.data.QuestItem;

public interface IGameManager {
    QuestItem getCurrentQuest();
    List<Monster> getMonstersInfo();
    List<Equipment> getEquipment();
}
