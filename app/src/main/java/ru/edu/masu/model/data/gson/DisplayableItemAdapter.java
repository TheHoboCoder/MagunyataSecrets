package ru.edu.masu.model.data.gson;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

import ru.edu.masu.model.entities.basic.IDisplayable;
import ru.edu.masu.model.entities.basic.ImageInfo;

// так как Json документ не полностью соответствует java классам
// (ImageInfo - объект, но в Json представлен строкой), то нужно описать процесс десериализации вручную.
// В Gson для этого существуют интерфейс JsonDeserializer<T> и более низкоуровневый абстрактный класс TypeAdapter
// type adapter реализовывает потоковое чтение и запись Json, а при использовани JsonDeserializer сначала
// читается документ и создается его модель в виде иерархии объектов. Поэтому с точки зрения производительности
// лучше использовать TypeAdapter (однако он менее удобен).
abstract public class DisplayableItemAdapter<T extends IDisplayable> implements ITypeAdapter<T> {

    public void write(JsonWriter out, T value) throws IOException {

    }
    // чтобы не использовать рефлекию
    public abstract T create();

    protected T readField(T item, String fieldName, JsonReader in) throws IOException {
        if(fieldName.equals("imgPath")){
            item.setImageInfo(new ImageInfo(in.nextString()));
        }
        return item;
    }

    public T read(JsonReader in) throws IOException {
        T item = create();
        in.beginObject();
        while(in.hasNext()){
            String fieldName = in.nextName();
            item = readField(item, fieldName, in);
        }
        in.endObject();
        return item;
    }
}
