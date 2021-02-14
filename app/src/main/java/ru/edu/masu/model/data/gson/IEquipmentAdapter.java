package ru.edu.masu.model.data.gson;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.HashMap;

import ru.edu.masu.model.entities.equipment.DescriptionEquipment;
import ru.edu.masu.model.entities.basic.DescriptionItem;
import ru.edu.masu.model.entities.equipment.DetailEquipment;
import ru.edu.masu.model.entities.equipment.IEquipment;
import ru.edu.masu.model.entities.equipment.SimpleEquipment;
import ru.edu.masu.model.entities.equipment.UrlEquipment;

public class IEquipmentAdapter extends MultiTypeAdapter<IEquipment> {

    private DescriptionItemAdapter<DescriptionItem> descriptionItemAdapter;

    public IEquipmentAdapter(DescriptionItemAdapter<DescriptionItem> descriptionItemAdapter) {
        this.descriptionItemAdapter = descriptionItemAdapter;
    }

    @Override
    public void write(JsonWriter out, IEquipment value) throws IOException {
        String type = getType(value);
        out.beginObject();
        out.name("type").value(type);
        out.name("name").value(value.getName());
        out.name("imgPath").value(value.getImageInfo().getImgPath());
        if(type.equals("url")){
            out.name("url").value(((UrlEquipment) value).getUrl());
        }
        if(type.equals("detail")){
            out.name("tag").value(((DetailEquipment) value).getTag());
        }
        if(type.equals("description")){
            out.beginObject();
            DescriptionItem descriptionItem = ((DescriptionEquipment) value).getEquipmentDesc();
            out.name("name").value(descriptionItem.getName());
            out.name("desc").value(descriptionItem.getDesc());
            out.name("imgPath").value(descriptionItem.getImageInfo().getImgPath());
            out.endObject();
        }
        out.endObject();
    }

    String getType(IEquipment item){
        String simpleName = item.getClass().getSimpleName();
        String realName = simpleName.substring(0, simpleName.indexOf("Equipment"));
        return realName.toLowerCase();
    }

    IEquipment createConcreteInstance(String type){

        if(type.equals("simple")){
            return new SimpleEquipment();
        }

        if(type.equals("url")){
            return new UrlEquipment();
        }

        if(type.equals("description")){
            return new DescriptionEquipment();
        }

        if(type.equals("detail")){
            return new DetailEquipment();
        }

        return null;
    }

    IEquipment setAllFields(HashMap<String, Object> fields, IEquipment item, String type){

        if(fields.containsKey("name") &&
                !fields.get("name").equals("")){
            item.setName((String)fields.get("name"));
        }

        if(fields.containsKey("imgPath") &&
                !fields.get("imgPath").equals("")){
            item.setName((String)fields.get("imgPath"));
        }

        if(fields.containsKey("url")){
            ((UrlEquipment)item).setUrl((String)fields.get("url"));
        }

        if(fields.containsKey("tag")){
            ((DetailEquipment)item).setTag((String)fields.get("tag"));
        }

        if(fields.containsKey("descEquipment")){
            ((DescriptionEquipment)item).setEquipmentDesc((DescriptionItem)fields.get("descEquipment"));
        }

        return item;

    }

    void setField(String field, HashMap<String, Object> fields, JsonReader in) throws IOException{
        if(field.equals("descEquipment")){
            fields.put("descEquipment", descriptionItemAdapter.read(in));
        }
        else{
            fields.put(field, in.nextString());
        }
    }
}
