package ru.edu.masu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import ru.edu.masu.adapters.MonsterInfoPagerAdapter;
import ru.edu.masu.data.Monster;
import ru.edu.masu.utils.ShadowTransformer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MeetActivity extends AppCompatActivity implements MonsterInfoFragment.IReadCallback {

    private int curReadCount = 0;
    private List<Monster> monsters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meet);
        //TODO: вставить реальные данные
        monsters = new ArrayList<>();
        monsters.add(new Monster(R.drawable.empty,getString(R.string.placeholder)));
        monsters.add(new Monster(R.drawable.empty,getString(R.string.placeholder)));
        monsters.add(new Monster(R.drawable.empty,getString(R.string.placeholder)));
        monsters.add(new Monster(R.drawable.empty,getString(R.string.placeholder)));
        monsters.add(new Monster(R.drawable.empty,getString(R.string.placeholder)));


        ViewPager pager = findViewById(R.id.pager);
        MonsterInfoPagerAdapter adapter =  new MonsterInfoPagerAdapter(getSupportFragmentManager(),
                                                    monsters,8, this);
        ShadowTransformer transformer = new ShadowTransformer(pager,adapter);
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(3);
    }

    //TODO: после переворта не работает
    @Override
    public void onRead() {
        curReadCount++;
        if(curReadCount >= monsters.size()){
            Button btn = findViewById(R.id.nextBtn);
            btn.setEnabled(true);
        }
    }

    public void onNextBtnClick(View view) {
        Intent intent = new Intent(MeetActivity.this,MainActivity.class);
        startActivity(intent);
    }
}
