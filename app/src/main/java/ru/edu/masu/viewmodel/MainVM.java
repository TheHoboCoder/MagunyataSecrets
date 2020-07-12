package ru.edu.masu.viewmodel;

import java.util.List;
import java.util.Objects;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import ru.edu.masu.model.IQuestPass;
import ru.edu.masu.model.IQuestPassHolder;
import ru.edu.masu.model.data.entities.Hint;
import ru.edu.masu.model.data.entities.QuestItem;
import ru.edu.masu.model.data.repository.IRepository;

public class MainVM extends ViewModel implements IQuestPassHolder {

    private MutableLiveData<QuestItem> currentQuest;
    public LiveData<QuestItem> getCurrentQuest(){
        return currentQuest;
    }

    private MutableLiveData<Integer> progress;
    public LiveData<Integer> getProgress(){
        return progress;
    }

    private MutableLiveData<Hint> currentHint;
    public LiveData<Hint> getCurrentHint(){
        return currentHint;
    }

    private IRepository<QuestItem> questRepository;
    private List<QuestItem> questItems;
    public List<QuestItem> getQuestItems(){
        if(questItems == null){
            questItems = questRepository.getAll();
        }
        return questItems;
    }

    private int currentHintIndex;

    public MainVM(IRepository<QuestItem> questRepository){
        this.questRepository = questRepository;
        progress = new MutableLiveData<>();
        progress.setValue(0);
        currentQuest = new MutableLiveData<>();
        setCurrentQuest(progress.getValue());
        currentHint = new MutableLiveData<>();
        setCurrentHint(0);
    }

    public void toNextQuest(){
        int progressValue = progress.getValue();
        QuestItem old = getCurrentQuest().getValue();
        progressValue++;
        progress.setValue(progressValue);
        setCurrentQuest(progressValue);
        currentHintIndex = 0;
        setCurrentHint(currentHintIndex);
    }

    public void toNextHint(){
        currentHintIndex++;
        setCurrentHint(currentHintIndex);
    }

    private void setCurrentQuest(int pos){
        if(pos > getQuestItems().size()-1){
            return;
        }
        QuestItem current = getQuestItems().get(pos);
        current.start();
        currentQuest.setValue(current);
    }

    private void setCurrentHint(int pos){
        QuestItem current = getCurrentQuest().getValue();
        if(current==null || pos > current.getQuestHints().size()-1){
            return;
        }
        Hint hint = current.getQuestHints().get(pos);
        currentHint.setValue(hint);
    }

    @Override
    public IQuestPass provideQuestPass() {
        return Objects.requireNonNull(currentQuest.getValue()).getQuestPass();
    }

    @Override
    public void check(IQuestPass questPass) {
        IQuestPass currentQuestPass = provideQuestPass();
        currentQuestPass.from(questPass);
        if(currentQuestPass.isPassed()){
            QuestItem current = Objects.requireNonNull(currentQuest.getValue());
            current.finish();
            currentQuest.setValue(current);
        }
    }
}
