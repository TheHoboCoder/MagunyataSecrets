package ru.edu.masu.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import ru.edu.masu.model.entities.basic.DescriptionItem;
import ru.edu.masu.model.entities.equipment.DetailEquipment;
import ru.edu.masu.model.entities.equipment.IEquipmentActionPerformer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class EquipmentActivity extends AppCompatActivity implements IEquipmentActionPerformer {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void routeToUrl(String url) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }

    @Override
    public void showDescription(DescriptionItem descriptionItem) {

    }

    @Override
    public void startDrag(DetailEquipment detailEquipment) {

    }
}