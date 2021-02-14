package ru.edu.masu.view.activities;

import androidx.fragment.app.Fragment;

import ru.edu.masu.model.entities.questPass.CodeQuestPass;
import ru.edu.masu.model.entities.questPass.IQuestPassVerifierProvider;
import ru.edu.masu.view.dialogs.CodeQuestPassFragment;
import ru.edu.masu.view.fragments.QRScanFragment;

public class QuestPassProvider implements IQuestPassVerifierProvider {

    private IQuestPassPerformer questPassPerformer;

    public QuestPassProvider(IQuestPassPerformer questPassPerformer) {
        this.questPassPerformer = questPassPerformer;
    }

    @Override
    public void createCodeQuestPass(CodeQuestPass codeQuestPass) {
        Fragment fragment = null;
        switch (codeQuestPass.getPassType()){
            case TEXT:
                fragment = CodeQuestPassFragment.newInstance();
                break;
            case QR:
                fragment = QRScanFragment.getInstance(codeQuestPass.getName());
                break;
        }
        if(codeQuestPass.getPassType() == CodeQuestPass.PassType.QR &&
                !questPassPerformer.canDisplayFullScreen()){
            questPassPerformer.navigateToAnother(codeQuestPass);
        }
        else{
            questPassPerformer.showQuestPassFragment(fragment);
        }

    }
}
