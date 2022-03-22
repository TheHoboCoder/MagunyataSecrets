package ru.edu.masu.model.data.gson;

import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ru.edu.masu.model.entities.quest.Hint;
import ru.edu.masu.model.entities.equipment.IEquipment;
import ru.edu.masu.model.entities.basic.ImageInfo;
import ru.edu.masu.model.entities.basic.NamedItem;
import ru.edu.masu.model.entities.quest.QuestItem;
import ru.edu.masu.model.entities.quest.StoryItem;

public class QuestItemAdapter extends DescriptionItemAdapter<QuestItem> {


    private ITypeAdapter<NamedItem> namedItemAdapter;
    private ITypeAdapter<StoryItem> storyItemAdapter;
    private ITypeAdapter<IEquipment> equipmentAdapter;
    private ITypeAdapter<Hint> hintTypeAdapter;

    public QuestItemAdapter(ITypeAdapter<NamedItem> namedItemAdapter,
                            ITypeAdapter<StoryItem> storyItemAdapter,
                            ITypeAdapter<IEquipment> equipmentAdapter,
                            ITypeAdapter<Hint> hintTypeAdapter) {
        this.namedItemAdapter = namedItemAdapter;
        this.storyItemAdapter = storyItemAdapter;
        this.equipmentAdapter = equipmentAdapter;
        this.hintTypeAdapter = hintTypeAdapter;
    }

    @Override
    public QuestItem create() {
        return new QuestItem();
    }

    List<StoryItem> readQuestStory(JsonReader in) throws IOException{
        List<StoryItem> storyItems = new ArrayList<>();
        in.beginArray();
        while(in.hasNext()){
            storyItems.add(storyItemAdapter.read(in));
        }
        in.endArray();
        return storyItems;
    }

    List<IEquipment> readEquipmentInfo(JsonReader in) throws IOException{
        List<IEquipment> equipments = new ArrayList<>();
        in.beginArray();
        while(in.hasNext()){
            equipments.add(equipmentAdapter.read(in));
        }
        in.endArray();
        return equipments;
    }

    List<Hint> readQuestHints(JsonReader in) throws IOException{
        List<Hint> hints = new ArrayList<>();
        in.beginArray();
        while(in.hasNext()) {
            hints.add(hintTypeAdapter.read(in));
        }
        in.endArray();
        return hints;
    }


    @Override
    protected QuestItem readField(QuestItem item, String fieldName, JsonReader in) throws IOException {
        QuestItem questItem = super.readField(item, fieldName, in);
        if(fieldName.equals("questTask")){
            questItem.setQuestTask(namedItemAdapter.read(in));
        }
        if(fieldName.equals("questProviderImg")){
            questItem.setQuestProviderImg(new ImageInfo(in.nextString()));
        }
        if(fieldName.equals("questHelperImg")){
            questItem.setQuestHelperImg(new ImageInfo(in.nextString()));
        }
        if(fieldName.equals("questStory")){
            questItem.setQuestStory(readQuestStory(in));
        }
        if(fieldName.equals("equipment")){
            questItem.setEquipment(readEquipmentInfo(in));
        }
        if(fieldName.equals("hints")){
            questItem.setQuestHints(readQuestHints(in));
        }
        if(fieldName.equals("questPassName")){
            questItem.setQuestPassName(in.nextString());
        }
        return questItem;
    }
}
