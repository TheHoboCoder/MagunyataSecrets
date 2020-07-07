package ru.edu.masu.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import ru.edu.masu.R;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

public class InventoryExplorerActivity extends AppCompatActivity {

    //private ArrayList<String> artifactNames;
    private ArrayList<Integer> artifactImages;
    private ArrayList<Integer> artifactTexts;

    private Button leftButton;
    private Button rightButton;

    //private TextView titleTextView;
    private ImageView imageView, textImageView;

    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_explorer);

        position = 0;
        leftButton = findViewById(R.id.inventoryLeftButton);
        rightButton = findViewById(R.id.inventoryRightButton);

        //titleTextView = findViewById(R.id.inventoryTitleTextView);
        imageView = findViewById(R.id.inventoryArtifactImageView);
        textImageView = findViewById(R.id.inventoryDescrBorderImageView);

        //artifactNames = new ArrayList<>();
        artifactImages = new ArrayList<>();
        artifactTexts = new ArrayList<>();

        //MASU VK
        //artifactNames.add(getResources().getString(R.string.artifact_masuvk_title));
        artifactImages.add(R.drawable.strazhnik);
        artifactImages.add(R.drawable.logistic);
        artifactImages.add(R.drawable.safe);

        artifactTexts.add(R.drawable.predystoria_kv1_1);
        artifactTexts.add(R.drawable.predystoria_kv1_2);
        artifactTexts.add(R.drawable.predystoria_kv1_3);

//        artifactTexts.add(getResources().getString(R.string.artifact_masuvk_text));
//
//        //ecobiopath
//        artifactNames.add(getResources().getString(R.string.artifact_ecobiopath_title));
//
//        artifactImages.add(R.drawable.artifact_ecobiopathmap);
//        artifactTexts.add(getResources().getString(R.string.artifact_ecobiopath_text));

        leftButton.setBackgroundResource(R.drawable.inventory_button_left_right_off);

        initAt(position);

        rightButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (position < artifactImages.size() - 1) {
                        position++;
                        initAt(position);
//                        if (position == artifactImages.size() - 1)
//                            rightButton.setBackgroundResource(R.drawable.inventory_button_left_right_off);
                        if (position == 1) leftButton.setBackgroundResource(R.drawable.inventory_button_left);
                    }
                    else{
                        Intent intent = new Intent(InventoryExplorerActivity.this,DescriptionActivity.class);
                        intent.putExtra("DESC","HELLO THERE");
                        startActivity(intent);
                    }
                }
        });
        leftButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (position > 0) {
                        position--;
                        initAt(position);
                        if (position == 0)
                            leftButton.setBackgroundResource(R.drawable.inventory_button_left_right_off);
                        if (position == artifactImages.size() - 2)
                            rightButton.setBackgroundResource(R.drawable.inventory_button_right);
                    }
                }

        });
    }

    private void initAt(int pos){
        //titleTextView.setText(artifactNames.get(pos));
        imageView.setImageResource(artifactImages.get(pos));
        textImageView.setImageResource(artifactTexts.get(pos));
    }

    public void nextAction(View view) {
        Intent intent = null;
        switch (position) {
            case 0:
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://vk.com/masu51"));
                startActivity(intent);
                break;
            case 1:
                intent = new Intent(InventoryExplorerActivity.this,EcoBioMapActivity.class);
                startActivity(intent);
                break;
        }
    }
}
