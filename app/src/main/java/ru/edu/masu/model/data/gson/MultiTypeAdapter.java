package ru.edu.masu.model.data.gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.util.HashMap;

// предназначен для десериализации подклассов суперкласса или интерфейса T.
// для этого в JSON должен быть указано поле type.
public abstract class MultiTypeAdapter<T> implements ITypeAdapter<T> {
    // создает по названию типа нужный объект подкласса T
    abstract T createConcreteInstance(String type);
    // так как поля для разных подклассов отличаются, то мы сохраняем название поле и его значение
    // в HashMap.
    abstract void setField(String field, HashMap<String, Object> fields, JsonReader in) throws IOException;
    // после чтения всех полей нужно достать их значения из HashMap и присвоить объекту.
    abstract T setAllFields(HashMap<String, Object> fields, T item, String type) throws InvalidObjectException;

    public T read(JsonReader in) throws IOException, InvalidObjectException {
        HashMap<String, Object> fields = new HashMap<>();
        String type = "";
        T item = null;
        in.beginObject();

        while(in.hasNext()){
            String fieldName = in.nextName();
            if(fieldName.equals("type")){
                type = in.nextString();
                item = createConcreteInstance(type);
            }
            else{
                setField(fieldName, fields, in);
            }
        }
        in.endObject();
        return setAllFields(fields, item, type);
    }
}
