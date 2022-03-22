package ru.edu.masu.model.data.repository;

import android.content.res.AssetManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import ru.edu.masu.model.data.gson.ITypeAdapter;
import ru.edu.masu.model.data.gson.StandartTypeAdapter;
import ru.edu.masu.model.entities.quest.Monster;

abstract public class BasicRepository<T> implements IRepository<T> {

    protected AssetManager assetManager;
    abstract protected String getFile();
    protected Gson gson;
    protected List<T> items;

    public BasicRepository(Class<T> typeClass,
                           AssetManager assetManager,
                           ITypeAdapter<T> typeAdapter) {
        this.assetManager = assetManager;
        gson = new GsonBuilder().
                registerTypeAdapter(typeClass,
                        new StandartTypeAdapter<T>(typeAdapter))
                .create();
    }

    // считываем JSON файл из ассетов в строку.
    protected String inputStreamToString() throws IOException {
        InputStream inputStream = assetManager.open(getFile());
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes, 0, bytes.length);
        String json = new String(bytes);
        inputStream.close();
        return json;
    }

    protected List<T> uploadGson() throws IOException{
        if(items != null){
            items = gson.fromJson(inputStreamToString(), new TypeToken<List<T>>(){}.getType());
        }
        return items;
    }

    @Override
    public List<T> getAll() throws IOException{
        return uploadGson();
    }


}
