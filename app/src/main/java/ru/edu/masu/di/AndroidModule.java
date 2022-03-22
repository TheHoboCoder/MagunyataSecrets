package ru.edu.masu.di;

import android.app.Application;
import android.content.res.AssetManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.edu.masu.utils.PreferencesWrapper;

import static android.content.Context.MODE_PRIVATE;

@Module
public class AndroidModule {

    @Provides
    @Singleton
    AssetManager provideAssetManager(Application app){
        return app.getAssets();
    }

    @Provides
    @Singleton
    PreferencesWrapper preferencesWrapper(Application app){
        return new PreferencesWrapper(
                app.getSharedPreferences(PreferencesWrapper.PREFERENCES_FILE, MODE_PRIVATE));
    }
}
