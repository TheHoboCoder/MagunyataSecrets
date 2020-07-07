package ru.edu.masu.view.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ru.edu.masu.R;
import ru.edu.masu.adapters.QuestItemRVMainAdapter;
import ru.edu.masu.model.data.entities.QuestItem;
import ru.edu.masu.model.IQuestFinished;
import ru.edu.masu.model.IQuestPass;
import ru.edu.masu.model.CodeQuestPass;
import ru.edu.masu.utils.QuestPassFragmentProvider;
import ru.edu.masu.view.dialogs.HintFragment;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private CountDownTimer timer;
    private QuestItem current;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<QuestItem> questItems = new ArrayList<>();
        current = new QuestItem(R.drawable.kiski, "Квест 1");
        questItems.add(current);
        for (int i = 2; i < 11; i++)  {
            questItems.add(new QuestItem(R.drawable.kiski, "Квест "+i));
        }

        current.start();
        final IQuestFinished questFinishedCallback = new IQuestFinished() {
            @Override
            public void onFinish() {
                Toast t = Toast.makeText(MainActivity.this,"Квест сдан",Toast.LENGTH_SHORT);
                t.show();
            }
            @Override
            public void onPassFailed() {
                Toast t = Toast.makeText(MainActivity.this,"Неверный код",Toast.LENGTH_SHORT);
                t.show();
            }
        };
        CodeQuestPass stringQuestPass = new CodeQuestPass("123");
        stringQuestPass.addCallback(questFinishedCallback);
        current.setQuestPass(stringQuestPass);

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
                    HintFragment fr = HintFragment.newInstance(R.drawable.podskazka_logistik_kv1,"Описание");
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



    public void inventoryClick(View view) {
        Intent intent = new Intent(MainActivity.this, InventoryExplorerActivity.class);
        startActivity(intent);
    }

    public void showDescription(View view) {
        Intent intent = new Intent(MainActivity.this, DescriptionActivity.class);
        intent.putExtra("DESC","HELLO_THERE");
        startActivity(intent);
    }

    public void onQuestPassBtnClick(View view) {
        IQuestPass questPass = current.getQuestPass();
        if(QuestPassFragmentProvider.isDialog(questPass)){
            DialogFragment fr = (DialogFragment) QuestPassFragmentProvider.get(questPass);
            fr.show(getSupportFragmentManager(),null);
        }
    }
}
