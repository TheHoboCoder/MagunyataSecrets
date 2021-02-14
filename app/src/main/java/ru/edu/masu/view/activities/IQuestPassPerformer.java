package ru.edu.masu.view.activities;

import androidx.fragment.app.Fragment;
import ru.edu.masu.model.entities.questPass.IQuestPass;

public interface IQuestPassPerformer {

    boolean canDisplayFullScreen();
    void showQuestPassFragment(Fragment questPassFragment);
    void navigateToAnother(IQuestPass questPass);
}
