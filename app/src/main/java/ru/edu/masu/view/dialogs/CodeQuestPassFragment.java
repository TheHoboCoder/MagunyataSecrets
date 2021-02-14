package ru.edu.masu.view.dialogs;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;
import ru.edu.masu.R;
import ru.edu.masu.model.entities.questPass.CodeQuestPass;
import ru.edu.masu.viewmodel.MainVM;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CodeQuestPassFragment extends BasicDialogFragment {

    private MainVM questPassVM;
    private EditText editText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        questPassVM = new ViewModelProvider(requireActivity()).get(MainVM.class);
    }

    public static CodeQuestPassFragment newInstance() {
        CodeQuestPassFragment fragment = new CodeQuestPassFragment();
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
        // к этому моменту метод сдачи должен уже быть загружен
        // но на всякий случай подписываемся на его изменения
        questPassVM.getQuestPassLiveData().observe(this, questPass -> {
            questPassVM.getQuestPassLiveData().removeObservers(this);
            CodeQuestPass codeQuestPass = (CodeQuestPass) questPass;
            codeQuestPass.enterCode(editText.getText().toString());
            if(codeQuestPass.isPassed()){
                questPassVM.finishQuest();
                this.dismiss();
            }
            else{
                Toast toast = Toast.makeText(this.getContext(),"Неверный код!",Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    @Override
    protected void onContentViewCreated(View view) {
        editText = view.findViewById(R.id.editText);
    }
}
