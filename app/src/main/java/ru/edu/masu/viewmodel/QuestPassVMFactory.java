package ru.edu.masu.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import ru.edu.masu.model.IQuestPass;

public class QuestPassVMFactory implements ViewModelProvider.Factory{

    private IQuestPass questPass;
    public QuestPassVMFactory(IQuestPass questPass){
        this.questPass = questPass;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(QuestPassVM.class)){
            return (T) new QuestPassVM(questPass);
        }
        throw new IllegalArgumentException("Wrong VM: "+modelClass.getName());
    }
}
