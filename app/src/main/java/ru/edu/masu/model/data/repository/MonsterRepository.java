package ru.edu.masu.model.data.repository;


import android.content.res.AssetManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import ru.edu.masu.model.entities.quest.Monster;
import ru.edu.masu.model.data.gson.MonsterItemAdapter;
import ru.edu.masu.model.data.gson.StandartTypeAdapter;

public class MonsterRepository extends BasicRepository<Monster>{

    private final String MONSTER_FILE = "monsters.json";

    public MonsterRepository(AssetManager assetManager){
        super(assetManager);
    }

    @Override
    public List<Monster> getAll() throws IOException {
        Gson gson = new GsonBuilder().
                registerTypeAdapter(Monster.class,
                        new StandartTypeAdapter<Monster>(new MonsterItemAdapter()))
                .create();
        List<Monster> monsters = new ArrayList<>();
        InputStream inputStream = assetManager.open(MONSTER_FILE);
        monsters = gson.fromJson(inputStreamToString(inputStream),
                                    new TypeToken<List<Monster>>(){}.getType());
        inputStream.close();
        return monsters;
    }

    @Override
    public void update(Monster item) {
        //TODO: сохранение статуса монстра(прочитана ли о нем информации)
    }
}
