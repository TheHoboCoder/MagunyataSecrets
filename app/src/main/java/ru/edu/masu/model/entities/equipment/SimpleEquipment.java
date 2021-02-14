package ru.edu.masu.model.entities.equipment;

import ru.edu.masu.model.entities.basic.NamedItem;
import ru.edu.masu.model.entities.equipment.IEquipment;
import ru.edu.masu.model.entities.equipment.IEquipmentActionPerformer;

// простой инвентарь - картинка и название
public class SimpleEquipment extends NamedItem implements IEquipment {

    @Override
    public void dispatchAction(IEquipmentActionPerformer actionPerformer) {
        // при нажатии ничего не делаем
    }
}
