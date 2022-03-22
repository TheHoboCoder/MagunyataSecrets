package ru.edu.masu.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import ru.edu.masu.view.activities.MainActivity;
import ru.edu.masu.view.activities.MeetActivity;
import ru.edu.masu.view.activities.StartActivity;

@Module
public abstract class ActivityModule {
    @ContributesAndroidInjector(
            modules = {ViewModelsModule.class})
    public abstract MainActivity contributeMainActivity();
    @ContributesAndroidInjector(
            modules = {ViewModelsModule.class})
    public abstract StartActivity contributeStartActivity();
    @ContributesAndroidInjector(
            modules = {ViewModelsModule.class})
    public abstract MeetActivity contributeMeetActivity();
}
