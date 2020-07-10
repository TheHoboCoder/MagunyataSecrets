package ru.edu.masu.viewmodel;

import androidx.lifecycle.ViewModel;
import ru.edu.masu.model.CodeQuestPass;

public class StringQuestPassVM extends ViewModel {

    private CodeQuestPass questPass;

    public StringQuestPassVM(CodeQuestPass questPass){
        this.questPass = questPass;
    }

    public boolean tryPass(String passCode){
        return questPass.enterCode(passCode);
    }

}
