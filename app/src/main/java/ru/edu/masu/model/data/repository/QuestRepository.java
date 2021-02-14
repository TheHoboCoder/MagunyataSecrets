package ru.edu.masu.model.data.repository;

import android.content.res.AssetManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import ru.edu.masu.model.entities.basic.DescriptionItem;
import ru.edu.masu.model.entities.basic.NamedItem;
import ru.edu.masu.model.entities.quest.QuestItem;
import ru.edu.masu.model.data.gson.DescriptionItemAdapter;
import ru.edu.masu.model.data.gson.HintTypeAdapter;
import ru.edu.masu.model.data.gson.IEquipmentAdapter;
import ru.edu.masu.model.data.gson.NamedItemAdapter;
import ru.edu.masu.model.data.gson.QuestItemAdapter;
import ru.edu.masu.model.data.gson.StandartTypeAdapter;
import ru.edu.masu.model.data.gson.StoryItemAdapter;

public class QuestRepository extends BasicRepository<QuestItem> {

    private final String QUESTS_FILE = "quests.json";

    public QuestRepository(AssetManager assetManager) {
        super(assetManager);
    }

    @Override
    public List<QuestItem> getAll() throws IOException {
        //todo
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(QuestItem.class, new StandartTypeAdapter<QuestItem>(
                        new QuestItemAdapter(new NamedItemAdapter<NamedItem>() {
                            @Override
                            public NamedItem create() {
                                return new NamedItem();
                            }
                        }, new StoryItemAdapter(), new IEquipmentAdapter(new DescriptionItemAdapter<DescriptionItem>() {
                            @Override
                            public DescriptionItem create() {
                                return new DescriptionItem();
                            }
                        }), new HintTypeAdapter())))
                .create();

        List<QuestItem> quests = new ArrayList<>();
        InputStream inputStream = assetManager.open(QUESTS_FILE);
        quests = gson.fromJson(inputStreamToString(inputStream),
                new TypeToken<List<QuestItem>>(){}.getType());
        inputStream.close();
        return quests;
    }

    @Override
    public void update(QuestItem item) {

    }
}
