package ru.edu.masu.utils;

import androidx.fragment.app.Fragment;
import ru.edu.masu.view.dialogs.CodeQuestPassFragment;
import ru.edu.masu.model.IQuestPass;
import ru.edu.masu.model.CodeQuestPass;
import ru.edu.masu.view.fragments.QRScanFragment;

public class QuestPassFragmentProvider {

    /*в зависимости от того, какой класс реализует IQuestPass,
      возвращает соответствующий фрагмент
    * */
    public static Fragment get(IQuestPass questPass){
        if(questPass instanceof CodeQuestPass){
            CodeQuestPass codeQuestPass = (CodeQuestPass) questPass;
            if(codeQuestPass.getPassType() == CodeQuestPass.PassType.TEXT){
                return CodeQuestPassFragment.newInstance(codeQuestPass);
            }
            else{
                return new QRScanFragment();
            }
        }
        return null;
    }

    public static boolean isDialog(IQuestPass questPass){
        //Add here all IQuestPass Implementations that should be shown as Dialog
        //their fragments must extend DialogFragment or it subclasses
        return (questPass instanceof CodeQuestPass
                && ((CodeQuestPass) questPass).getPassType() == CodeQuestPass.PassType.TEXT);
    }
}
