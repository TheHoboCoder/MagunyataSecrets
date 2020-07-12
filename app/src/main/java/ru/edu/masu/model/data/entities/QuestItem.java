package ru.edu.masu.model.data.entities;

import java.util.ArrayList;
import java.util.List;

import ru.edu.masu.model.IQuestPass;

public class QuestItem {

    private int image;
    private String name;
    private String task;
    private int taskImage;
    //изображение монстра квестодателя
    private int questProviderImg;
    //изображение монстра помощника
    private int questHelperImg;
    private List<StoryItem> questStory;
    private List<Equipment> equipment;
    private List<Hint> questHints;
    private IQuestPass questPass;

    public enum Status{
        LOCKED,
        ACTIVE,
        FINISHED
    }

    private Status status;

    public QuestItem(int image, String name) {
        this.image = image;
        this.name = name;
        this.status = Status.LOCKED;
    }


    public int getImage() { return image; }
    public String getName() { return name; }

    public void start(){
        status = Status.ACTIVE;
    }

    public void finish(){
        status = Status.FINISHED;
    }

    public Status getStatus() { return status; }

    public void setQuestPass(IQuestPass questPass) {
        this.questPass = questPass;
    }

    public IQuestPass getQuestPass() {
        return questPass;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public int getTaskImage() {
        return taskImage;
    }

    public void setTaskImage(int taskImage) {
        this.taskImage = taskImage;
    }

    public int getQuestProviderImg() {
        return questProviderImg;
    }

    public void setQuestProviderImg(int questProviderImg) {
        this.questProviderImg = questProviderImg;
    }

    public int getQuestHelperImg() {
        return questHelperImg;
    }

    public void setQuestHelperImg(int questHelperImg) {
        this.questHelperImg = questHelperImg;
    }

    public List<StoryItem> getQuestStory() {
        return questStory;
    }

    public void setQuestStory(List<StoryItem> questStory) {
        this.questStory = questStory;
    }

    public List<Equipment> getEquipment() {
        return equipment;
    }

    public void setEquipment(ArrayList<Equipment> equipment) {
        this.equipment = equipment;
    }

    public List<Hint> getQuestHints() {
        return questHints;
    }

    public void setQuestHints(List<Hint> questHints) {
        this.questHints = questHints;
    }




}

