package ru.edu.masu.model.data.repository;

import android.content.res.AssetManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import ru.edu.masu.model.data.gson.ITypeAdapter;
import ru.edu.masu.model.entities.questPass.IQuestPass;
import ru.edu.masu.model.data.gson.IQuestPassAdapter;
import ru.edu.masu.model.data.gson.StandartTypeAdapter;

public class QuestPassRepository extends BasicRepository<IQuestPass> {

    @Override
    protected String getFile() {
        return "quest_pass_methods.json";
    }

    HashMap<String, IQuestPass> questPassHashMap;

    @Inject
    public QuestPassRepository(AssetManager assetManager, ITypeAdapter<IQuestPass> typeAdapter) {
        super(IQuestPass.class, assetManager, typeAdapter);
    }

    @Override
    protected List<IQuestPass> uploadGson() throws IOException{
         super.uploadGson();
         if(questPassHashMap == null){
             questPassHashMap = new HashMap<>(items.size());
             for(IQuestPass questPass:items){
                 questPassHashMap.put(questPass.getName(), questPass);
             }
         }
         return items;
    }


    public IQuestPass getByName(String name) throws IOException{
        uploadGson();
        return questPassHashMap.get(name);
    }

    @Override
    public void update(IQuestPass item) {

    }
}
