package ru.edu.masu.model.entities.equipment;

import ru.edu.masu.model.entities.basic.INamedItem;

// при нажатии на инвентарь в зависимости от его типа могут выполняться разные действия.
// (Например, открытие ссылки или показ всплывающего окна с описанием этого инвентаря)
// Но помещать код, связанный с Android, в классы модели не очень желательно.
// Поэтому используется паттерн посетитель: действия, которые могут выполняться при нажатии на инвентарь,
// вынесены в интерфейс IEquipmentActionPerformer.
// Классы инвентаря реализуют интерфейс IEquipment с методом dispatchAction и вызывают нужный им метод
// у IEquipmentActionPerformer.
public interface IEquipment extends INamedItem {
    void dispatchAction(IEquipmentActionPerformer actionPerformer);
}
