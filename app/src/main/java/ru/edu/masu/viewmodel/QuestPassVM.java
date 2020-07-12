package ru.edu.masu.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import ru.edu.masu.model.IQuestPass;
import ru.edu.masu.model.IQuestPassHolder;

public class QuestPassVM extends ViewModel implements IQuestPassHolder {

    private IQuestPass questPass;
    private MutableLiveData<Boolean> isPassed;
    public LiveData<Boolean> getPassStatus(){
        return isPassed;
    }

    public QuestPassVM(IQuestPass questPass){
        this.questPass = questPass;
        isPassed = new MutableLiveData<>();
    }

    @Override
    public IQuestPass provideQuestPass() {
        return questPass;
    }

    @Override
    public void check(IQuestPass questPass) {
        questPass.from(questPass);
        isPassed.setValue(questPass.isPassed());
    }
}
