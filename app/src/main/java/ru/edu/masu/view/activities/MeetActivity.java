package ru.edu.masu.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;
import ru.edu.masu.R;
import ru.edu.masu.adapters.MonsterInfoPagerAdapter;
import ru.edu.masu.model.data.repository.MonsterRepository;
import ru.edu.masu.utils.ShadowTransformer;
import ru.edu.masu.viewmodel.BasicVMFactory;
import ru.edu.masu.viewmodel.MonsterVM;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MeetActivity extends AppCompatActivity{

    private MonsterVM monsterVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meet);

        monsterVM = new ViewModelProvider(this, new BasicVMFactory(new MonsterRepository()))
                .get(MonsterVM.class);
        ViewPager pager = findViewById(R.id.pager);
        MonsterInfoPagerAdapter adapter = new MonsterInfoPagerAdapter(getSupportFragmentManager(),
                                                    monsterVM.getMonsters(),8,
                                                    getApplicationContext());
        ShadowTransformer transformer = new ShadowTransformer(pager,adapter);
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(3);
        Button btn = findViewById(R.id.nextBtn);
        monsterVM.getRead().observe(this, btn::setEnabled);
    }


    public void onNextBtnClick(View view) {
        Intent intent = new Intent(MeetActivity.this,InventoryExplorerActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }
}
