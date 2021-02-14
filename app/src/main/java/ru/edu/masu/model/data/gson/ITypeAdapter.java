package ru.edu.masu.model.data.gson;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public interface ITypeAdapter<T> {
    void write(JsonWriter out, T value) throws IOException;
    T read(JsonReader in) throws IOException;
}
