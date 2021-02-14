package ru.edu.masu.model.data.gson;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.util.HashMap;

import ru.edu.masu.model.entities.questPass.CodeQuestPass;
import ru.edu.masu.model.entities.questPass.IQuestPass;

public class IQuestPassAdapter extends MultiTypeAdapter<IQuestPass> {

    @Override
    public void write(JsonWriter out, IQuestPass value) throws IOException {

    }

    @Override
    IQuestPass createConcreteInstance(String type) {
        if(type.equals("codeQuestPass")){
            return new CodeQuestPass();
        }
        return null;
    }

    @Override
    void setField(String field, HashMap<String, Object> fields, JsonReader in) throws IOException {
        if(field.equals("code") || field.equals("passType") || field.equals("name")){
            fields.put(field, in.nextString());
        }
    }

    @Override
    IQuestPass setAllFields(HashMap<String, Object> fields, IQuestPass item, String type) throws InvalidObjectException {
        if(fields.containsKey("name")){
            item.setName((String)fields.get("name"));
        }
        else{
            throw new InvalidObjectException("quest pass object must contain name field");
        }
        if(type.equals("codeQuestPass")){
            CodeQuestPass codeQuestPass = (CodeQuestPass) item;
            if(fields.containsKey("code") && fields.containsKey("passType")){
                codeQuestPass.setPassCode((String)fields.get("code"));
                String passType = (String)fields.get("passType");
                if(passType.equals("qr")){
                    codeQuestPass.setPassType(CodeQuestPass.PassType.QR);
                }
                else if(passType.equals("text")){
                    codeQuestPass.setPassType(CodeQuestPass.PassType.TEXT);
                }
                else{
                    throw new InvalidObjectException("unknown pass type "+passType);
                }
                return codeQuestPass;
            }
            throw new InvalidObjectException("wrong fields for CodeQuestPass: expected code and passType");
        }
        throw new InvalidObjectException("there's no IQuestPass class with type as "+type);
    }
}
