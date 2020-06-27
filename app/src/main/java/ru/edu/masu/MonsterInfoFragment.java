package ru.edu.masu;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

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
    private static final String MONSTER_PIC = "monster_pic";
    private static final String MONSTER_DESC = "monster_desc";
    private static final String MONSTER_NAME = "monster_name";

    //picId - id ресурса drawable изображения
    private int picId;
    private String monsterDesc, monsterName;

    @Nullable
    public CardView getCardView() {
        return cardView;
    }

    private CardView cardView;

    private static final String IS_READ = "is_read";
    private boolean isRead = false;

    public MonsterInfoFragment() {
        // Required empty public constructor
    }

    //обратный вызов при нажатии на кнопку Ок
    public interface IReadCallback{
        void onRead();
    }

    private IReadCallback readListener;

    public void setReadListener(IReadCallback readListener) {
        this.readListener = readListener;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param picId drawable res id.
     * @param desc Parameter description.
     * @return A new instance of fragment MonsterInfoFragment.
     */
    public static MonsterInfoFragment newInstance(int picId, String name, String desc) {
        MonsterInfoFragment fragment = new MonsterInfoFragment();
        Bundle args = new Bundle();
        args.putInt(MONSTER_PIC, picId);
        args.putString(MONSTER_DESC, desc);
        args.putString(MONSTER_NAME, name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            picId = getArguments().getInt(MONSTER_PIC);
            monsterDesc = getArguments().getString(MONSTER_DESC);
            monsterName = getArguments().getString(MONSTER_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_monster_info,container, false);
        ImageView monsterPic = view.findViewById(R.id.monsterPic);
        TextView monsterNameTxt = view.findViewById(R.id.monsterName);
        monsterNameTxt.setText(monsterName);
        TextView monsterDescTxt = view.findViewById(R.id.descTxt);
        monsterPic.setImageDrawable(getResources().getDrawable(picId));
        monsterDescTxt.setText(monsterDesc);
        final Button btn = view.findViewById(R.id.okBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(readListener!=null && !isRead){
                    readListener.onRead();
                }
                isRead = true;
                btn.setEnabled(false);
            }
        });
        if(isRead) btn.setEnabled(false);
        cardView = (CardView) view;
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(MONSTER_PIC,picId);
        outState.putString(MONSTER_DESC,monsterDesc);
        //TODO: сохранение не работает
        outState.putBoolean(IS_READ,isRead);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState!=null){
            picId = savedInstanceState.getInt(MONSTER_PIC);
            monsterDesc = savedInstanceState.getString(MONSTER_DESC);
            isRead = savedInstanceState.getBoolean(IS_READ);
        }
    }
}
