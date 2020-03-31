package ru.edu.masu;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ru.edu.masu.adapters.QuestItemRVMainAdapter;
import ru.edu.masu.data.QuestItem;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<QuestItem> questItems = new ArrayList<>();
        questItems.add(new QuestItem(R.drawable.kiski, "Квест 01", "Пройдено"));
        questItems.add(new QuestItem(R.drawable.kiski, "Квест 2", "Пройдено"));
        questItems.add(new QuestItem(R.drawable.kiski, "Квест 3", "Текущий"));
        for (int i = 4; i < 11; i++)  {
            questItems.add(new QuestItem(R.drawable.kiski, "Квест "+i, "Недоступно"));
        }
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        adapter = new QuestItemRVMainAdapter(questItems, this);
        layoutManager = new GridLayoutManager(this,2);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //TODO: вывод диалога с подтверждением выхода
    }
}
