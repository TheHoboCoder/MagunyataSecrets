package ru.edu.masu.model.entities.equipment;

import ru.edu.masu.model.entities.basic.DescriptionItem;

// действия, которые могут выполняться при нажатии на инвентарь
public interface IEquipmentActionPerformer {
    void routeToUrl(String url);
    void showDescription(DescriptionItem descriptionItem);
    // DetailEquipment можно перетаскивать на поле миниигры
    void startDrag(DetailEquipment detailEquipment);
}
