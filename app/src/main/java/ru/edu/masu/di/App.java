package ru.edu.masu.di;

import android.app.Application;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;


public class App extends DaggerApplication {

//    private AppComponent appComponent;
//    public AppComponent getAppComponent(){
//        return appComponent;
//    }
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        appComponent = DaggerAppComponent.builder().
//                        androidModule(new AndroidModule(this))
//                        .build();
//    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }
}
