package ru.edu.masu.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import ru.edu.masu.model.data.entities.QuestItem;
import ru.edu.masu.model.data.repository.IRepository;

public class MainVMFactory implements ViewModelProvider.Factory {
    private IRepository<QuestItem> questItemIRepository;

    public MainVMFactory(IRepository<QuestItem> questItemIRepository) {
        this.questItemIRepository = questItemIRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(MainVM.class)){
            return (T) new MainVM(questItemIRepository);
        }
        return null;
    }
}
