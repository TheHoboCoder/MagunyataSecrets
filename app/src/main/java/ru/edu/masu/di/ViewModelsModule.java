package ru.edu.masu.di;

import androidx.lifecycle.ViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import ru.edu.masu.viewmodel.MainVM;
import ru.edu.masu.viewmodel.MonsterVM;
import ru.edu.masu.viewmodel.QuestPassVM;

@Module
public abstract class ViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(MonsterVM.class)
    public abstract ViewModel bindMonsterVM(MonsterVM viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(QuestPassVM.class)
    public abstract ViewModel bindQuestPassVM(QuestPassVM viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(MainVM.class)
    public abstract ViewModel bindMainVM(MainVM mainVM);
}
