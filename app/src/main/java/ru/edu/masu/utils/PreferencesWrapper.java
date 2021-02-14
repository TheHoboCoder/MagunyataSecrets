package ru.edu.masu.utils;

import android.content.SharedPreferences;

public class PreferencesWrapper {

    public static final String CURRENT_QUEST = "current_quest";
    public static final String CURRENT_HINT = "current_hint";
    public static final String IS_GAME_RUNNED = "is_game_runned";

    public static final String PREFERENCES_FILE = "preferences_file";

    private SharedPreferences preferences;

    public PreferencesWrapper(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    public int getCurrentQuestIndex(){
        return preferences.getInt(PreferencesWrapper.CURRENT_QUEST, 0);
    }

    public void setCurrentQuestIndex(int index){
        preferences.edit()
                .putInt(PreferencesWrapper.CURRENT_QUEST, index)
                .apply();
    }

    public void setCurrentHintIndex(int index){
        preferences.edit()
                .putInt(PreferencesWrapper.CURRENT_HINT, index)
                .apply();
    }

    public int getCurrentHintIndex(){
        return preferences.getInt(PreferencesWrapper.CURRENT_HINT, 0);
    }

    public void setIsGameRunned(boolean isGameRunned){
        preferences.edit()
                .putBoolean(IS_GAME_RUNNED, isGameRunned)
                .apply();
    }

    public boolean isGameRunned(){
        return preferences.getBoolean(IS_GAME_RUNNED, false);
    }


}
