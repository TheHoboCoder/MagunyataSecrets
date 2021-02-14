package ru.edu.masu.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import ru.edu.masu.model.entities.quest.QuestItem;
import ru.edu.masu.model.data.repository.IRepository;
import ru.edu.masu.model.data.repository.QuestPassRepository;
import ru.edu.masu.utils.PreferencesWrapper;

public class MainVMFactory implements ViewModelProvider.Factory {

    private IRepository<QuestItem> questItemIRepository;
    private QuestPassRepository questPassRepository;
    private PreferencesWrapper preferencesWrapper;

    public MainVMFactory(IRepository<QuestItem> questItemIRepository,
                         QuestPassRepository questPassRepository,
                         PreferencesWrapper preferencesWrapper) {
        this.questItemIRepository = questItemIRepository;
        this.questPassRepository = questPassRepository;
        this.preferencesWrapper = preferencesWrapper;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(MainVM.class)){
            return (T) new MainVM(questItemIRepository, questPassRepository, preferencesWrapper);
        }
        throw new IllegalArgumentException("Wrong VM");
    }
}
