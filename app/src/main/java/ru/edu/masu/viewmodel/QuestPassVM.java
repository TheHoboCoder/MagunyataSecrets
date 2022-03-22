package ru.edu.masu.viewmodel;

import java.io.IOException;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import ru.edu.masu.model.entities.questPass.IQuestPass;
import ru.edu.masu.model.data.repository.QuestPassRepository;

public class QuestPassVM extends ViewModel  {

    private MutableLiveData<Boolean> isPassed;
    public LiveData<Boolean> getPassStatus(){
        return isPassed;
    }

    private MutableLiveData<IQuestPass> currentQuestPass;
    public LiveData<IQuestPass> getCurrentQuest(String name){
        if(currentQuestPass.getValue() == null){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        IQuestPass questPass = questPassRepository.getByName(name);
                        currentQuestPass.postValue(questPass);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        return currentQuestPass;
    }
    private QuestPassRepository questPassRepository;

    @Inject
    public QuestPassVM(QuestPassRepository questPassRepository){
        this.questPassRepository = questPassRepository;
        currentQuestPass = new MutableLiveData<>();
        isPassed = new MutableLiveData<>();
    }

    public void check(IQuestPass questPass) {
        questPass.from(questPass);
        isPassed.setValue(questPass.isPassed());
    }
}
