package ru.edu.masu.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import ru.edu.masu.R;
import ru.edu.masu.model.entities.questPass.IQuestPass;
import ru.edu.masu.model.data.repository.QuestPassRepository;
import ru.edu.masu.viewmodel.QuestPassVM;
import ru.edu.masu.viewmodel.QuestPassVMFactory;
import ru.edu.masu.viewmodel.ViewModelProviderFactory;

import android.content.Intent;
import android.os.Bundle;

import javax.inject.Inject;

public class QuestPassActivity extends AppCompatActivity implements IQuestPassPerformer{

    public static final int REQUEST_CODE = 10;
    public static final int QUEST_PASSED = 5;
    public static final String QUEST_NAME = "quest_name";
    private QuestPassVM questPassVM;
    private QuestPassProvider questPassProvider;

    @Inject
    ViewModelProviderFactory vmFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass);
        String questPassName = getIntent().getStringExtra(QUEST_NAME);
        questPassProvider = new QuestPassProvider(this);
        questPassVM = new ViewModelProvider(this, vmFactory).get(QuestPassVM.class);
        questPassVM.getCurrentQuest(questPassName).observe(this, questPass -> {
            questPass.createVerifier(questPassProvider);
        });
        questPassVM.getPassStatus().observe(this, status -> {
            if(status){
                Intent finishIntent =  new Intent();
                setResult(QUEST_PASSED, finishIntent);
                finish();
            }
        });

    }

    @Override
    public boolean canDisplayFullScreen() {
        return true;
    }

    @Override
    public void showQuestPassFragment(Fragment questPassFragment) {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .add(R.id.container, questPassFragment,"questPassFragment")
                .commit();
    }

    @Override
    public void navigateToAnother(IQuestPass questPass) {

    }
}
