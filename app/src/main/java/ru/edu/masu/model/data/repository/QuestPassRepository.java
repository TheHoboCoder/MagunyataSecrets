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

import ru.edu.masu.model.entities.questPass.IQuestPass;
import ru.edu.masu.model.data.gson.IQuestPassAdapter;
import ru.edu.masu.model.data.gson.StandartTypeAdapter;

public class QuestPassRepository extends BasicRepository<IQuestPass> {

    private final String PATH = "quest_pass_methods.json";

    HashMap<String, IQuestPass> questPassHashMap;
    List<IQuestPass> questPasses;

    public QuestPassRepository(AssetManager assetManager) {
        super(assetManager);
    }

    public void upload() throws IOException {
        if(questPasses == null){
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(IQuestPass.class,
                            new StandartTypeAdapter<IQuestPass>(new IQuestPassAdapter()))
                    .create();
            questPasses = new ArrayList<>();
            InputStream inputStream = assetManager.open(PATH);
            questPasses = gson.fromJson(inputStreamToString(inputStream),
                    new TypeToken<List<IQuestPass>>(){}.getType());
            inputStream.close();
            questPassHashMap = new HashMap<>(questPasses.size());
            for(IQuestPass questPass:questPasses){
                questPassHashMap.put(questPass.getName(), questPass);
            }
        }
    }

    @Override
    public List<IQuestPass> getAll() throws IOException {
        upload();
        return questPasses;
    }

    public IQuestPass getByName(String name) throws IOException{
        upload();
        return questPassHashMap.get(name);
    }

    @Override
    public void update(IQuestPass item) {

    }
}
