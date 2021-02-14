package ru.edu.masu.model.entities.quest;

import java.util.List;

import ru.edu.masu.model.entities.basic.DescriptionItem;
import ru.edu.masu.model.entities.basic.ImageInfo;
import ru.edu.masu.model.entities.basic.NamedItem;
import ru.edu.masu.model.entities.equipment.IEquipment;

public class QuestItem extends DescriptionItem {

    private NamedItem questTask;
    //изображение монстра квестодателя
    private ImageInfo questProviderImg;
    //изображение монстра помощника
    private ImageInfo questHelperImg;

    private List<StoryItem> questStory;
    private List<IEquipment> equipment;
    private List<Hint> questHints;
    // идентификатор метода сдачи квеста
    private String questPassName;

    public enum Status{
        LOCKED,
        ACTIVE,
        FINISHED
    }

    private Status status;

    public QuestItem() {
        this.status = Status.LOCKED;
    }

    public void start(){
        status = Status.ACTIVE;
    }

    public void finish(){
        status = Status.FINISHED;
    }

    public Status getStatus() { return status; }

    public NamedItem getQuestTask() {
        return questTask;
    }

    public void setQuestTask(NamedItem questTask) {
        this.questTask = questTask;
    }

    public ImageInfo getQuestProviderImg() {
        return questProviderImg;
    }

    public void setQuestProviderImg(ImageInfo questProviderImg) {
        this.questProviderImg = questProviderImg;
    }

    public ImageInfo getQuestHelperImg() {
        return questHelperImg;
    }

    public void setQuestHelperImg(ImageInfo questHelperImg) {
        this.questHelperImg = questHelperImg;
    }

    public List<StoryItem> getQuestStory() {
        return questStory;
    }

    public void setQuestStory(List<StoryItem> questStory) {
        this.questStory = questStory;
    }

    public List<IEquipment> getEquipment() {
        return equipment;
    }

    public void setEquipment(List<IEquipment> equipment) {
        this.equipment = equipment;
    }

    public List<Hint> getQuestHints() {
        return questHints;
    }

    public void setQuestHints(List<Hint> questHints) {
        this.questHints = questHints;
    }

    public String getQuestPassName() {
        return questPassName;
    }

    public void setQuestPassName(String questPassName) {
        this.questPassName = questPassName;
    }

}

