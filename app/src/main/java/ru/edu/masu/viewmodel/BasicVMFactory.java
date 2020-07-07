package ru.edu.masu.viewmodel;

import java.lang.reflect.InvocationTargetException;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class BasicVMFactory implements ViewModelProvider.Factory {

    private final Object[] constructorParams;

    public BasicVMFactory(Object... constructorParams){
        this.constructorParams = constructorParams;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        Class<?>[] classes = new Class<?>[constructorParams.length];
        for (int i = 0; i < constructorParams.length; i++) {
            classes[i] = constructorParams[i].getClass();
        }
        try {
            return modelClass.getConstructor(classes).newInstance(constructorParams);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("wrong parameters");
    }
}
