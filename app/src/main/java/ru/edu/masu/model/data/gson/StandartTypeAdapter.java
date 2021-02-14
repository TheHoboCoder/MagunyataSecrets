package ru.edu.masu.model.data.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

import androidx.annotation.NonNull;

// у нас получается много вложенных TypeAdapter-ов, в каждом из которых, помимо нужных нам методов
// write и read есть еще какой-то код от Gson. Для описания сериализации/десериализации нам нужно
// переопределить только эти методы, поэтому был создан интерфейс ITypeAdapter<T>.
// Классы, описывающие сериализацию/десериализацию наследуются не от TypeAdapter<T>, а расширяют этот интерфейс.
// Поэтому в этих содержится только код, который нам нужен и мы не привязаны к TypeAdapter.
public class StandartTypeAdapter<T> extends TypeAdapter<T> {

    private final ITypeAdapter<T> typeAdapterInterface;

    public StandartTypeAdapter(@NonNull ITypeAdapter<T> typeAdapterInterface) {
        this.typeAdapterInterface = typeAdapterInterface;
    }

    @Override
    public void write(JsonWriter out, T value) throws IOException {
        typeAdapterInterface.write(out, value);
    }

    @Override
    public T read(JsonReader in) throws IOException {
        return typeAdapterInterface.read(in);
    }
}
