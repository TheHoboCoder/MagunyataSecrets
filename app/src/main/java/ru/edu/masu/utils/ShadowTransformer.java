package ru.edu.masu.utils;


import android.view.View;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;
import ru.edu.masu.adapters.MonsterInfoPagerAdapter;

// см. https://github.com/DevExchanges/ViewPagerCards
public class ShadowTransformer implements ViewPager.PageTransformer, ViewPager.OnPageChangeListener {

    private ViewPager pager;
    private MonsterInfoPagerAdapter adapter;
    private float lastOffset;

    public ShadowTransformer(ViewPager pager, MonsterInfoPagerAdapter adapter){
        this.pager = pager;
        pager.addOnPageChangeListener(this);
        pager.setPageTransformer(false,this);
        this.adapter = adapter;
    }

    @Override
    public void transformPage(@NonNull View page, float position) {


    }

    //при скролле уменьшает текущий фрагмент и увеличивает следующую
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        int realCurrentPosition;
        int nextPosition;
        float baseElevation = adapter.getBaseElevation();
        float realOffset;

        boolean goingLeft = lastOffset > positionOffset;

        if(goingLeft){
            realCurrentPosition = position+1;
            nextPosition = position;
            realOffset = 1 - positionOffset;
        }
        else{
            nextPosition = position + 1;
            realCurrentPosition = position;
            realOffset = positionOffset;
        }

        if(nextPosition>adapter.getCount() - 1
            || realCurrentPosition > adapter.getCount() - 1)
            return;

        CardView currentCard = adapter.getViewAt(realCurrentPosition);

        if(currentCard!=null){
            currentCard.setScaleX((float)(1+0.1*(1-realOffset)));
            currentCard.setScaleY((float)(1+0.1*(1-realOffset)));
            currentCard.setCardElevation((baseElevation +
                    baseElevation*(MonsterInfoPagerAdapter.MAX_ELEVATION - 1)*(1 - realOffset)));
        }


        CardView nextCard = adapter.getViewAt(nextPosition);

        if(currentCard!=null){
            nextCard.setScaleX((float)(1+0.1*realOffset));
            nextCard.setScaleY((float)(1+0.1*realOffset));
            nextCard.setCardElevation((baseElevation +
                    baseElevation*(MonsterInfoPagerAdapter.MAX_ELEVATION - 1)*realOffset));
        }


        lastOffset = positionOffset;

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
