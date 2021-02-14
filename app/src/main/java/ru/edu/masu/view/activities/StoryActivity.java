package ru.edu.masu.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;

import java.util.List;

import ru.edu.masu.R;
import ru.edu.masu.adapters.StoryItemAdapter;
import ru.edu.masu.model.entities.basic.NamedItem;
import ru.edu.masu.model.entities.quest.QuestItem;
import ru.edu.masu.model.entities.quest.StoryItem;
import ru.edu.masu.model.data.repository.QuestPassRepository;
import ru.edu.masu.model.data.repository.QuestRepository;
import ru.edu.masu.utils.PreferencesWrapper;
import ru.edu.masu.viewmodel.MainVM;
import ru.edu.masu.viewmodel.MainVMFactory;

public class StoryActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private MainVM mainVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);
        viewPager = findViewById(R.id.viewPager);
        viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        AssetManager assetManager = getAssets();
        mainVM = new ViewModelProvider(this,
                new MainVMFactory(new QuestRepository(assetManager),
                        new QuestPassRepository(assetManager),
                        new PreferencesWrapper(
                                getSharedPreferences(PreferencesWrapper.PREFERENCES_FILE, MODE_PRIVATE))))
                .get(MainVM.class);
        mainVM.getCurrentQuest().observe(this, this::onCurrentQuestLoaded);
    }

    private void onCurrentQuestLoaded(QuestItem currentQuest){
        List<StoryItem> questStory = currentQuest.getQuestStory();
        final NamedItem namedItem = currentQuest.getQuestTask();
        viewPager.setAdapter(new StoryItemAdapter(questStory,
                scrollPosition -> {
                    if(scrollPosition < questStory.size()){
                        viewPager.setCurrentItem(scrollPosition, true);
                    }
                    else{
                        Intent intent = new Intent(StoryActivity.this, DescriptionActivity.class);
                        intent.putExtra(DescriptionActivity.DESC, namedItem.getName());
                        intent.putExtra(DescriptionActivity.IMG_PATH, namedItem.getImageInfo().getImgPath());
                        startActivity(intent);
                    }
                }));
    }
}