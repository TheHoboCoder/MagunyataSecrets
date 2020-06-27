package ru.edu.masu;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class QuestPassFragment extends DialogFragment {

    private final String QUEST_CODE = "1234";
    private EditText editText;
    private Button btn;

    public QuestPassFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pass_quest_fragment,container, false);
        btn = view.findViewById(R.id.okBtn);
        editText = view.findViewById(R.id.editText);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.getText().toString().equals(QUEST_CODE)){
                    Toast t  = Toast.makeText(getContext(),"Квест заврешен!",Toast.LENGTH_LONG);
                    t.show();
                }
                else{
                    Toast t  = Toast.makeText(getContext(),"Неверный пароль!",Toast.LENGTH_LONG);
                    t.show();
                }
            }
        });
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return view;
    }
}
