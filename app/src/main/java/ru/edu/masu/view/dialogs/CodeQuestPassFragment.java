package ru.edu.masu.view.dialogs;

import android.os.Bundle;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import ru.edu.masu.R;
import ru.edu.masu.model.CodeQuestPass;
import ru.edu.masu.model.data.repository.QuestRepository;
import ru.edu.masu.viewmodel.MainVM;
import ru.edu.masu.viewmodel.MainVMFactory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

public class CodeQuestPassFragment extends BasicDialogFragment {

    private CodeQuestPass questPass;
    private MainVM questPassVM;
    private EditText editText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        questPassVM = new ViewModelProvider(requireActivity()).get(MainVM.class);
    }

    public void setQuestPass(CodeQuestPass questPass){
        this.questPass = questPass;
    }


    public static CodeQuestPassFragment newInstance(CodeQuestPass questPass) {
        CodeQuestPassFragment fragment = new CodeQuestPassFragment();
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
        CodeQuestPass codeQuestPass = (CodeQuestPass) questPassVM.provideQuestPass();
        codeQuestPass.enterCode(editText.getText().toString());
        if(codeQuestPass.isPassed()){
            questPassVM.check(codeQuestPass);
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
