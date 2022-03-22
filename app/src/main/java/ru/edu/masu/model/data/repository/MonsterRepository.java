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
import ru.edu.masu.model.entities.quest.Monster;
import ru.edu.masu.model.data.gson.MonsterItemAdapter;
import ru.edu.masu.model.data.gson.StandartTypeAdapter;

public class MonsterRepository extends BasicRepository<Monster>{

    @Inject
    public MonsterRepository(AssetManager assetManager, ITypeAdapter<Monster> typeAdapter){
        super(Monster.class, assetManager, typeAdapter);
    }

    @Override
    protected String getFile() {
        return "monsters.json";
    }

    @Override
    public void update(Monster item) {
        //TODO: сохранение статуса монстра(прочитана ли о нем информации)
    }
}
