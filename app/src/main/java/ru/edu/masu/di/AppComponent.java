package ru.edu.masu.di;

import android.app.Application;

import com.google.android.datatransport.runtime.dagger.Component;

import javax.inject.Singleton;

import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

import dagger.BindsInstance;
import ru.edu.masu.model.data.repository.MonsterRepository;
import ru.edu.masu.model.data.repository.QuestPassRepository;
import ru.edu.masu.model.data.repository.QuestRepository;
import ru.edu.masu.view.activities.MainActivity;
import ru.edu.masu.view.activities.MeetActivity;
import ru.edu.masu.view.activities.StartActivity;

@Singleton
@Component(modules = {AndroidSupportInjectionModule.class,
                      GsonModule.class,
                      AndroidModule.class,
                        ViewModelFactoryModule.class})
public interface AppComponent extends AndroidInjector<App> {

    MonsterRepository getMonsterRep();
    QuestPassRepository getQuestPassRep();
    QuestRepository getQuestRep();

    @Component.Builder
    interface Builder{

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }


//    void inject(StartActivity startActivity);
//    void inject(MainActivity mainActivity);
//    void inject(MeetActivity meetActivity);
}
