package ru.edu.masu.viewmodel;

import java.util.List;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import ru.edu.masu.model.data.entities.Monster;
import ru.edu.masu.model.data.repository.MonsterRepository;

public class MonsterVM extends ViewModel {

    private MutableLiveData<Boolean> isAllRead;
    private MonsterRepository monsterRepository;
    private List<Monster> monsters;

    private int readCount;

    public LiveData<Boolean> getRead(){
        if(isAllRead == null){
            isAllRead = new MutableLiveData<>();
            isAllRead.setValue(false);
        }
        return isAllRead;
    }

    public List<Monster> getMonsters(){
        if(monsters == null){
            monsters = this.monsterRepository.getAll();
        }
        return monsters;
    }

    public MonsterVM(MonsterRepository monsterRepository){
        this.monsterRepository = monsterRepository;
        readCount = 0;
    }

    public void setRead(Monster monster){
        //TODO: предполагается, что id это порядковый номер
        monster = monsters.get(monster.getMonsterId()-1);
        if(monster.isMet()){
           return;
        }
        monster.setMet(true);
        //monsterRepository.update(monster);
        readCount++;
        if(readCount == monsters.size()){
            isAllRead.setValue(true);
        }
    }

}
