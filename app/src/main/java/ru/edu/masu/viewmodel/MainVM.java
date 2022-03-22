package ru.edu.masu.viewmodel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import ru.edu.masu.model.entities.questPass.IQuestPass;
import ru.edu.masu.model.entities.quest.Hint;
import ru.edu.masu.model.entities.quest.QuestItem;
import ru.edu.masu.model.data.repository.IRepository;
import ru.edu.masu.model.data.repository.QuestPassRepository;
import ru.edu.masu.utils.PreferencesWrapper;

public class MainVM extends ViewModel {

    private MediatorLiveData<QuestItem> currentQuest;
    public LiveData<QuestItem> getCurrentQuest(){
        return currentQuest;
    }

    private MutableLiveData<Integer> progress;
    public LiveData<Integer> getProgress(){
        return progress;
    }

    private MediatorLiveData<Hint> currentHint;
    public LiveData<Hint> getCurrentHint(){
        return currentHint;
    }


    private MutableLiveData<List<QuestItem>> questItems;
    private MutableLiveData<IQuestPass> questPassLiveData;
    public LiveData<IQuestPass> getQuestPassLiveData(){
        return questPassLiveData;
    }

    private void uploadQuestPass(QuestItem currentQuest){
        new Thread(new Runnable() {
            @Override
            public void run() {
                IQuestPass questPass = null;
                try {
                    questPass = questPassRepository.getByName(currentQuest.getQuestPassName());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                questPassLiveData.postValue(questPass);
            }
        }).start();
    }

    private PreferencesWrapper preferences;
    private QuestPassRepository questPassRepository;
    private IRepository<QuestItem> questRepository;

    public MutableLiveData<List<QuestItem>> getQuestItems(){
        if(questItems.getValue() == null){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    List<QuestItem> value = new ArrayList<>();
                    try {
                        value = questRepository.getAll();
                    } catch (IOException e) {
                        //todo:
                        e.printStackTrace();
                    }
                    questItems.postValue(value);
                }
            }).start();
        }
        return questItems;
    }

    @Inject
    public MainVM(IRepository<QuestItem> questRepository,
                  QuestPassRepository questPassRepository,
                  PreferencesWrapper preferencesWrapper){
        this.questRepository = questRepository;
        this.preferences = preferencesWrapper;
        this.questPassRepository = questPassRepository;
        questPassLiveData = new MutableLiveData<>();
        questItems = new MutableLiveData<>();
        currentQuest = new MediatorLiveData<>();
        // после загрузки всех квестов должен быть установлен текущий квест
        currentQuest.addSource(questItems, items -> {
            QuestItem value = items.get(preferences.getCurrentQuestIndex());
            startQuest(value);
        });

        currentHint = new MediatorLiveData<>();
        currentHint.addSource(currentQuest, questItem -> {
                    if(questItem.getStatus() == QuestItem.Status.FINISHED)
                        return;
                    currentHint.setValue(questItem.getQuestHints().get(preferences.getCurrentHintIndex()));
                    uploadQuestPass(questItem);
         });
        progress = new MutableLiveData<>();
        // вызываем, чтобы начать загрузку квестов
        getQuestItems();
    }

    private void startQuest(QuestItem questItem){
        questItem.start();
        preferences.setCurrentHintIndex(0);
        progress.setValue(preferences.getCurrentQuestIndex());
        currentQuest.setValue(questItem);
    }

    public void toNextQuest(){
        if(getQuestItems().getValue() != null){
            int currentIndex = preferences.getCurrentQuestIndex() + 1;
            preferences.setCurrentQuestIndex(currentIndex);
            questPassLiveData.setValue(null);
            QuestItem nextQuest = getQuestItems().getValue().get(currentIndex);
            startQuest(nextQuest);
        }
    }

    public void toNextHint(){
        int currentHintIndex = preferences.getCurrentHintIndex() + 1;
        QuestItem current = getCurrentQuest().getValue();
        if(current != null && currentHintIndex < current.getQuestHints().size()){
            preferences.setCurrentHintIndex(currentHintIndex);
            Hint hint = current.getQuestHints().get(currentHintIndex);
            currentHint.setValue(hint);
        }

    }

    public void finishQuest() {
        IQuestPass currentQuestPass = null;
        if(currentQuest.getValue() != null && questPassLiveData.getValue() != null){
            currentQuestPass = questPassLiveData.getValue();
        }
        else{
            return;
        }
        QuestItem current = currentQuest.getValue();
        current.finish();
        currentQuest.setValue(current);
    }
}
