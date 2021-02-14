package ru.edu.masu.model.entities.equipment;

import ru.edu.masu.model.entities.basic.NamedItem;
import ru.edu.masu.model.entities.equipment.IEquipment;
import ru.edu.masu.model.entities.equipment.IEquipmentActionPerformer;

// инвентарь, который может использоваться в минииграх - например, кусок паззла
// содержит тэг - уникальную строку, по которой минигра узнает, какой этот элемент и как его использовать
public class DetailEquipment extends NamedItem implements IEquipment {

    @Override
    public void dispatchAction(IEquipmentActionPerformer actionPerformer) {
        actionPerformer.startDrag(this);
    }

    private String tag;


    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
