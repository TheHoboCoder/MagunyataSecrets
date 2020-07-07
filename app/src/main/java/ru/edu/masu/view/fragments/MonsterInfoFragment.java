package ru.edu.masu.view.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import ru.edu.masu.R;
import ru.edu.masu.model.data.entities.Monster;
import ru.edu.masu.viewmodel.MonsterVM;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MonsterInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MonsterInfoFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String MONSTER = "MONSTER";

    Monster monster;
    MonsterVM monsterVM;

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
     * @param monster Monster.
     * @return A new instance of fragment MonsterInfoFragment.
     */
    public static MonsterInfoFragment newInstance(Monster monster) {
        MonsterInfoFragment fragment = new MonsterInfoFragment();
        Bundle args = new Bundle();
        args.putParcelable(MONSTER, monster);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            monster = (Monster) getArguments().get(MONSTER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_monster_info,container, false);
        monsterVM = new ViewModelProvider(requireActivity()).get(MonsterVM.class);
        ImageView monsterPic = view.findViewById(R.id.monsterPic);
        TextView monsterNameTxt = view.findViewById(R.id.monsterName);
        //TODO: убрать context и getString, когда будет реализована загрузка монстров
        Context context = monsterNameTxt.getContext();
        //monsterNameTxt.setText(monster.getMonsterName());
        monsterNameTxt.setText(context.getString(monster.getMonsterNameId()));
        TextView monsterDescTxt = view.findViewById(R.id.descTxt);
        //monsterDescTxt.setText(monster.getMonsterDesc());
        monsterDescTxt.setText(context.getString(monster.getMonsterDescId()));
        monsterPic.setImageDrawable(getResources().getDrawable(monster.getPicId()));
        final Button btn = view.findViewById(R.id.okBtn);
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
        cardView = (CardView) view;
        return view;
    }

}
