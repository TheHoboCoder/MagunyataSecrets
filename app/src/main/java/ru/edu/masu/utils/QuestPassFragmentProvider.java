package ru.edu.masu.utils;

import androidx.fragment.app.Fragment;
import ru.edu.masu.view.dialogs.StringQuestPassFragment;
import ru.edu.masu.model.IQuestPass;
import ru.edu.masu.model.CodeQuestPass;

public class QuestPassFragmentProvider {

    /*в зависимости от того, какой класс реализует IQuestPass,
      возвращает соответствующий фрагмент
    * */
    public static Fragment get(IQuestPass questPass){
        if(questPass instanceof CodeQuestPass){
            return StringQuestPassFragment.newInstance((CodeQuestPass)questPass);
        }
        return null;
    }

    public static boolean isDialog(IQuestPass questPass){
        //Add here all IQuestPass Implementations that should be shown as Dialog
        //their fragments must extend DialogFragment or it subclasses
        return (questPass instanceof CodeQuestPass);
    }
}
