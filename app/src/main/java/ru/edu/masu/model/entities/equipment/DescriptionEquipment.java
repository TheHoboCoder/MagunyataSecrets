package ru.edu.masu.model.entities.equipment;

import ru.edu.masu.model.entities.basic.DescriptionItem;
import ru.edu.masu.model.entities.basic.NamedItem;
import ru.edu.masu.model.entities.equipment.IEquipment;
import ru.edu.masu.model.entities.equipment.IEquipmentActionPerformer;

// инвентарь с описанием - содержит DescriptionItem
public class DescriptionEquipment extends NamedItem implements IEquipment {

    private DescriptionItem equipmentDesc;

    @Override
    public void dispatchAction(IEquipmentActionPerformer actionPerformer) {
        actionPerformer.showDescription(getEquipmentDesc());
    }

    public DescriptionItem getEquipmentDesc() {
        return equipmentDesc;
    }

    public void setEquipmentDesc(DescriptionItem equipmentDesc) {
        this.equipmentDesc = equipmentDesc;
    }
}
