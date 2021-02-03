package ru.edu.masu.model.data.repository;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.Resources;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.FileDescriptor;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import ru.edu.masu.R;
import ru.edu.masu.model.data.entities.Monster;

public class MonsterRepository implements IRepository<Monster>{


    private AssetManager assetManager;
    private final String MONSTER_FILE = "monsters.json";

    public MonsterRepository(AssetManager assetManager){
        this.assetManager = assetManager;
    }

    public String inputStreamToString(InputStream inputStream) {
        try {
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes, 0, bytes.length);
            String json = new String(bytes);
            return json;
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public List<Monster> getAll() {
        Gson gson = new GsonBuilder().create();
        List<Monster> monsters = new ArrayList<>();
        try {
            InputStream inputStream = assetManager.open(MONSTER_FILE);
            monsters = gson.fromJson(inputStreamToString(inputStream),
                                        new TypeToken<List<Monster>>(){}.getType());
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return monsters;
    }

    @Override
    public void update(Monster item) {
        //TODO: сохранение статуса монстра(прочитана ли о нем информации)
    }
}
