package ru.edu.masu.model.entities.equipment;

import ru.edu.masu.model.entities.basic.NamedItem;
import ru.edu.masu.model.entities.equipment.IEquipment;
import ru.edu.masu.model.entities.equipment.IEquipmentActionPerformer;

public class UrlEquipment extends NamedItem implements IEquipment {

    private String url;

    @Override
    public void dispatchAction(IEquipmentActionPerformer actionPerformer) {
        actionPerformer.routeToUrl(getUrl());
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
