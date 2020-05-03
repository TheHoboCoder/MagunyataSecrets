package ru.edu.masu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class InventoryExplorerActivity extends AppCompatActivity {

    private ArrayList<String> artifactNames;
    private ArrayList<Integer> artifactImages;
    private ArrayList<String> artifactTexts;

    private Button leftButton;
    private Button rightButton;

    private TextView titleTextView;
    private ImageView imageView;
    private TextView textView;

    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_explorer);

        position = 0;
        leftButton = findViewById(R.id.inventoryLeftButton);
        rightButton = findViewById(R.id.inventoryRightButton);

        titleTextView = findViewById(R.id.inventoryTitleTextView);
        imageView = findViewById(R.id.inventoryArtifactImageView);
        textView = findViewById(R.id.inventoryArtifactDescriptionTextView);

        artifactNames = new ArrayList<>();
        artifactImages = new ArrayList<>();
        artifactTexts = new ArrayList<>();

        //MASU VK
        artifactNames.add(getResources().getString(R.string.artifact_masuvk_title));
        artifactImages.add(R.drawable.artifact_masuvk);
        artifactTexts.add(getResources().getString(R.string.artifact_masuvk_text));

        //ecobiopath
        artifactNames.add(getResources().getString(R.string.artifact_ecobiopath_title));
        artifactImages.add(R.drawable.artifact_ecobiopathmap);
        artifactTexts.add(getResources().getString(R.string.artifact_ecobiopath_text));

        titleTextView.setText(artifactNames.get(position));
        imageView.setImageResource(artifactImages.get(position));
        textView.setText(artifactTexts.get(position));
        leftButton.setBackgroundResource(R.drawable.inventory_button_left_right_off);

        rightButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (position < artifactImages.size() - 1) {
                        position++;
                        int id = artifactImages.get(position);
                        titleTextView.setText(artifactNames.get(position));
                        imageView.setImageResource(id);
                        textView.setText(artifactTexts.get(position));
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
                        int id = artifactImages.get(position);
                        titleTextView.setText(artifactNames.get(position));
                        imageView.setImageResource(id);
                        textView.setText(artifactTexts.get(position));
                        if (position == 0)
                            leftButton.setBackgroundResource(R.drawable.inventory_button_left_right_off);
                        if (position == artifactImages.size() - 2)
                            rightButton.setBackgroundResource(R.drawable.inventory_button_right);
                    }
                }

        });
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
