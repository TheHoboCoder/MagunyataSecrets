package ru.edu.masu.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import ru.edu.masu.model.data.repository.QuestPassRepository;

public class QuestPassVMFactory implements ViewModelProvider.Factory{

    private QuestPassRepository questPassRepository;

    public QuestPassVMFactory(QuestPassRepository questPassRepository){
        this.questPassRepository = questPassRepository;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(QuestPassVM.class)){
            return (T) new QuestPassVM(questPassRepository);
        }
        throw new IllegalArgumentException("Wrong VM: "+modelClass.getName());
    }
}
