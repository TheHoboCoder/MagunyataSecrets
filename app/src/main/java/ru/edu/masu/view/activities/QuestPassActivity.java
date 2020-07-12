package ru.edu.masu.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import ru.edu.masu.R;
import ru.edu.masu.model.IQuestPass;
import ru.edu.masu.utils.QuestPassFragmentProvider;
import ru.edu.masu.viewmodel.QuestPassVM;
import ru.edu.masu.viewmodel.QuestPassVMFactory;

import android.content.Intent;
import android.os.Bundle;

public class QuestPassActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 10;
    public static final int QUEST_PASSED = 0;
    public static final String IQUEST_PASS_PARCEABLE = "IQuestPass";
    private QuestPassVM questPassVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_quest_pass);
        IQuestPass questPass = getIntent().getParcelableExtra(IQUEST_PASS_PARCEABLE);
        questPassVM = new ViewModelProvider(this, new QuestPassVMFactory(questPass))
                .get(QuestPassVM.class);
        questPassVM.getPassStatus().observe(this, status -> {
            if(status){
                onQuestPassed();
            }
        });
        Fragment questPassFragment = QuestPassFragmentProvider.get(questPass);
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .add(R.id.container, questPassFragment,"questPassFragment")
                .commit();
    }

    public void onQuestPassed(){
        Intent finishIntent =  new Intent();
        finishIntent.putExtra(IQUEST_PASS_PARCEABLE, questPassVM.provideQuestPass());
        setResult(QUEST_PASSED, finishIntent);
        finish();
    }
}
