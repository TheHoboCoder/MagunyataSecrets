package ru.edu.masu.viewmodel;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import ru.edu.masu.model.entities.quest.Monster;
import ru.edu.masu.model.data.repository.MonsterRepository;

public class MonsterVM extends ViewModel {

    private MutableLiveData<Boolean> isAllRead;
    private MonsterRepository monsterRepository;
    private MutableLiveData<List<Monster>> monsters;

    private int readCount;

    public LiveData<Boolean> getRead(){
        return isAllRead;
    }

    public MutableLiveData<List<Monster>> getMonsters(){

        if(monsters == null){
            monsters = new MutableLiveData<>();
            //todo: use executor
            new Thread(new Runnable() {
                @Override
                public void run() {
                    List<Monster> value = null;
                    //todo:
                    try {
                        value = monsterRepository.getAll();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    monsters.postValue(value);
                }
            }).start();
        }
        return monsters;
    }

    @Inject
    public MonsterVM(MonsterRepository monsterRepository){
        this.monsterRepository = monsterRepository;
        isAllRead = new MutableLiveData<>();

        isAllRead.setValue(false);
        readCount = 0;
    }

    public void setRead(Monster monster){
        List<Monster> monsterList = monsters.getValue();
        if (monsterList == null || monster.isMet())
            return;
        monster.setMet(true);
        readCount++;
        if(readCount == monsters.getValue().size()){
            isAllRead.setValue(true);
        }
    }

}
