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
        monsters = new ArrayList<>();
        monsters.add(new Monster(R.drawable.strazhnik,"Страж", getString(R.string.quard_desc)));
        monsters.add(new Monster(R.drawable.sessia_sketch,"Сессия",getString(R.string.session_desc)));
        monsters.add(new Monster(R.drawable.logistic,"Логистик", getString(R.string.logistic_desc)));
        monsters.add(new Monster(R.drawable.neryakha_sketch,"Неряха",getString(R.string.neryaha_desc)));
        monsters.add(new Monster(R.drawable.sociofob,"Социофоб",getString(R.string.sociofob_desc)));
        monsters.add(new Monster(R.drawable.golodny_sketch,"Голодный",getString(R.string.hungry_desc)));
        monsters.add(new Monster(R.drawable.byurokrat_sketch,"Бюрократ",getString(R.string.burea_desc)));
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
        Intent intent = new Intent(MeetActivity.this,InventoryExplorerActivity.class);
        startActivity(intent);
    }
}
