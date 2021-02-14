package ru.edu.masu.model.data.repository;

import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;

abstract public class BasicRepository<T> implements IRepository<T> {

    protected AssetManager assetManager;

    public BasicRepository(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    // считываем JSON файл из ассетов в строку.
    protected String inputStreamToString(InputStream inputStream) {
        try {
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes, 0, bytes.length);
            String json = new String(bytes);
            return json;
        } catch (IOException e) {
            return null;
        }
    }

}
