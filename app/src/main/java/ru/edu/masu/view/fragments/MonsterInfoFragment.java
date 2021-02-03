package ru.edu.masu.view.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import ru.edu.masu.R;
import ru.edu.masu.model.data.entities.Monster;
import ru.edu.masu.utils.ImageCaching;
import ru.edu.masu.viewmodel.MonsterVM;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MonsterInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MonsterInfoFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String MONSTER_ID = "MONSTER_ID";

    int monsterId;
    MonsterVM monsterVM;
    private ImageView monsterPic;
    private TextView monsterDescTxt;
    private TextView monsterNameTxt;
    private Button btn;

    @Nullable
    public CardView getCardView() {
        return cardView;
    }

    private CardView cardView;

    public MonsterInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param monsterId int.
     * @return A new instance of fragment MonsterInfoFragment.
     */
    public static MonsterInfoFragment newInstance(int monsterId) {
        MonsterInfoFragment fragment = new MonsterInfoFragment();
        Bundle args = new Bundle();
        args.putInt(MONSTER_ID, monsterId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            monsterId =  getArguments().getInt(MONSTER_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_monster_info,container, false);
        monsterVM = new ViewModelProvider(requireActivity()).get(MonsterVM.class);

        monsterPic = view.findViewById(R.id.monsterPic);
        monsterNameTxt = view.findViewById(R.id.monsterName);
        monsterDescTxt = view.findViewById(R.id.descTxt);
        btn = view.findViewById(R.id.okBtn);

        monsterVM.getMonsters().observe(this, this::onMonstersLoad);
        cardView = (CardView) view;
        return view;
    }

    public void onMonstersLoad(List<Monster> monsters){
        Context context = monsterNameTxt.getContext();
        Monster monster = monsters.get(monsterId);
        //monsterNameTxt.setText(monster.getMonsterName());
        monsterNameTxt.setText(monster.getName());
        //monsterDescTxt.setText(monster.getMonsterDesc());
        monsterDescTxt.setText(monster.getDesc());
        ImageCaching.loadIn(context, monster.getImgPath(), monsterPic);

        if(monster.isMet()){
            btn.setEnabled(false);
        }
        else{
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    monsterVM.setRead(monster);
                    btn.setEnabled(false);
                }
            });
        }
    }

}
