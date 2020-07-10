package ru.edu.masu.view.dialogs;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;
import ru.edu.masu.R;
import ru.edu.masu.model.CodeQuestPass;
import ru.edu.masu.model.IQuestFinished;
import ru.edu.masu.viewmodel.BasicVMFactory;
import ru.edu.masu.viewmodel.StringQuestPassVM;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class StringQuestPassFragment extends BasicDialogFragment {

    private CodeQuestPass questPass;
    private StringQuestPassVM questPassVM;
    private EditText editText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        questPassVM = new ViewModelProvider(this, new BasicVMFactory(questPass))
                .get(StringQuestPassVM.class);
    }

    public void setQuestPass(CodeQuestPass questPass){
        this.questPass = questPass;
    }


    public static StringQuestPassFragment newInstance(CodeQuestPass questPass) {
        StringQuestPassFragment fragment = new StringQuestPassFragment();
        fragment.setQuestPass(questPass);
        Bundle args = packArgs(R.layout.pass_quest_fragment,R.string.pass_quest);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void unpackArgs(Bundle args){
        super.unpackArgs(args);
    }

    @Override
    protected void onActionBtnClick(View v){
        if(questPassVM.tryPass(editText.getText().toString())){
            this.dismiss();
        }
        else{
            Toast toast = Toast.makeText(this.getContext(),"Неверный код!",Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    protected void onContentViewCreated(View view) {
        editText = view.findViewById(R.id.editText);
    }
}
