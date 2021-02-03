package ru.edu.masu.adapters;

import android.content.Context;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import ru.edu.masu.view.fragments.MonsterInfoFragment;
import ru.edu.masu.model.data.entities.Monster;

public class MonsterInfoPagerAdapter extends FragmentPagerAdapter {

    private List<Monster> monsters;
    private List<MonsterInfoFragment> fragments;

    public static final  int MAX_ELEVATION = 10;

    public float getBaseElevation() {
        return baseElevation;
    }

    public CardView getViewAt(int pos){
        return fragments.get(pos).getCardView();
    }

    private float baseElevation;

    public MonsterInfoPagerAdapter(@NonNull FragmentManager fm, List<Monster> monsters,
                                   int baseElevation,
                                   Context context) {
        super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.monsters = monsters;
        this.baseElevation =  baseElevation;
        fragments = new ArrayList<>(monsters.size());
        for(Monster monster: monsters){
            //MonsterInfoFragment fragment = MonsterInfoFragment.newInstance(monster.picId, monster.monsterName, monster.monsterDesc);
            MonsterInfoFragment fragment = MonsterInfoFragment.newInstance(monsters.indexOf(monster));
            fragments.add(fragment);
        }
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        MonsterInfoFragment fragment = (MonsterInfoFragment) super.instantiateItem(container,position);
        fragments.set(position,fragment);
        return fragment;
    }

    @Override
    public int getCount() {
        return monsters.size();
    }


}
