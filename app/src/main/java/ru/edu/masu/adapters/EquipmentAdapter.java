package ru.edu.masu.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ru.edu.masu.R;
import ru.edu.masu.model.entities.equipment.IEquipment;
import ru.edu.masu.model.entities.equipment.IEquipmentActionPerformer;
import ru.edu.masu.utils.ImageCaching;

public class EquipmentAdapter extends BasicRVAdapter<IEquipment, EquipmentAdapter.EquipmentVH> {

    private IEquipmentActionPerformer actionPerformer;

    public EquipmentAdapter(List<IEquipment> items, int layoutId, IEquipmentActionPerformer actionPerformer) {
        super(items, layoutId);
    }

    @Override
    EquipmentVH createVH(View view) {
        return new EquipmentVH(view);
    }

    @Override
    void bind(IEquipment item, EquipmentVH viewholder, int position) {
        viewholder.equipmentNameTxt.setText(item.getName());
        Context context = viewholder.equipmentNameTxt.getContext();
        ImageCaching.loadIn(context, item.getImageInfo().getImgPath(), viewholder.equipmentImage);
        viewholder.equipmentImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.dispatchAction(actionPerformer);
            }
        });
    }

    class EquipmentVH extends RecyclerView.ViewHolder{

        TextView equipmentNameTxt;
        ImageView equipmentImage;

        public EquipmentVH(@NonNull View itemView) {
            super(itemView);
            equipmentNameTxt = itemView.findViewById(R.id.equipmentName);
            equipmentImage = itemView.findViewById(R.id.equipmentImage);
        }
    }
}
