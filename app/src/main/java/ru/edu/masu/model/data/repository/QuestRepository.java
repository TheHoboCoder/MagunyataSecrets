package ru.edu.masu.model.data.repository;

import android.content.res.AssetManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ru.edu.masu.model.data.gson.ITypeAdapter;
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

    @Inject
    public QuestRepository(AssetManager assetManager, ITypeAdapter<QuestItem> typeAdapter) {
        super(QuestItem.class, assetManager, typeAdapter);
    }

    @Override
    protected String getFile() {
        return "quests.json";
    }

    @Override
    public void update(QuestItem item) {

    }
}
