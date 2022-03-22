package ru.edu.masu.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;
import ru.edu.masu.R;
import ru.edu.masu.adapters.MonsterInfoPagerAdapter;
import ru.edu.masu.di.App;
import ru.edu.masu.model.entities.quest.Monster;
import ru.edu.masu.model.data.repository.MonsterRepository;
import ru.edu.masu.utils.ShadowTransformer;
import ru.edu.masu.viewmodel.BasicVMFactory;
import ru.edu.masu.viewmodel.MonsterVM;
import ru.edu.masu.viewmodel.ViewModelProviderFactory;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.List;

import javax.inject.Inject;

public class MeetActivity extends AppCompatActivity{

    private MonsterVM monsterVM;

    @Inject
    ViewModelProviderFactory factory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //((App)getApplication()).getAppComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meet);
        monsterVM = new ViewModelProvider(this, factory).get(MonsterVM.class);

        Button btn = findViewById(R.id.nextBtn);
        monsterVM.getRead().observe(this, btn::setEnabled);
        monsterVM.getMonsters().observe(this, this::onMonstersLoaded);
    }


    public void onMonstersLoaded(List<Monster> monsters){
        MonsterInfoPagerAdapter adapter = new MonsterInfoPagerAdapter(getSupportFragmentManager(),
                monsters,8,
                getApplicationContext());
        ViewPager pager = findViewById(R.id.pager);
        ShadowTransformer transformer = new ShadowTransformer(pager,adapter);
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(3);
    }


    public void onNextBtnClick(View view) {
        Intent intent = new Intent(MeetActivity.this, StoryActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }
}
