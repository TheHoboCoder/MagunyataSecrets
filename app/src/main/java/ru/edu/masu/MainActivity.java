package ru.edu.masu;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ru.edu.masu.adapters.QuestItemRVMainAdapter;
import ru.edu.masu.data.QuestItem;
import ru.edu.masu.utils.ScanActivity;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private CountDownTimer timer;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<QuestItem> questItems = new ArrayList<>();
        questItems.add(new QuestItem(R.drawable.kiski, "Квест 1", "Текущий",R.drawable.bg2));

        for (int i = 2; i < 11; i++)  {
            questItems.add(new QuestItem(R.drawable.kiski, "Квест "+i, "Недоступно",R.drawable.bg3));
        }
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        adapter = new QuestItemRVMainAdapter(questItems, this);
        layoutManager = new GridLayoutManager(this,2);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        if(savedInstanceState==null){
            timer = new CountDownTimer(2000,2000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    HintFragment fr = HintFragment.newInstance(R.drawable.sociofob,"Описание");
                    fr.setCancelable(false);
                    fr.show(getSupportFragmentManager(),"hint");
                }
            };
            timer.start();
        }

    }

    @Override
    public void onBackPressed() {
        AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
               .setMessage(R.string.quitGame)
               .setPositiveButton(R.string.cancel, new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       dialog.dismiss();
                   }
                })
                .setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.super.onBackPressed();
                    }
                })
                .create();
        dialog.show();
    }

    //example using ScanActivity
    //TODO:remove this
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ScanActivity.REQUEST_SCAN_ACTIVITY &&
                resultCode == ScanActivity.RESULT_BARCODE_FOUND ){
            String resData = data.getStringExtra(ScanActivity.BARCODE_TEXT);
            Toast toast = Toast.makeText(MainActivity.this, resData, Toast.LENGTH_LONG);
            toast.show();
        }
    }

    public void testCamera(View view) {
        Intent intent = new Intent(MainActivity.this, ScanActivity.class);
        startActivityForResult(intent,ScanActivity.REQUEST_SCAN_ACTIVITY);
    }

    public void inventoryClick(View view) {
        Intent intent = new Intent(MainActivity.this, InventoryExplorerActivity.class);
        startActivity(intent);
    }

    public void showDescription(View view) {
        Intent intent = new Intent(MainActivity.this, DescriptionActivity.class);
        intent.putExtra("DESC","HELLO_THERE");
        startActivity(intent);
    }
}
