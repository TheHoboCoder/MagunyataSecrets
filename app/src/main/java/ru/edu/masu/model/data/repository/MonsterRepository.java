package ru.edu.masu.model.data.repository;

import java.util.ArrayList;
import java.util.List;

import ru.edu.masu.R;
import ru.edu.masu.model.data.entities.Monster;

public class MonsterRepository implements IRepository<Monster>{

    @Override
    public List<Monster> getAll() {
        ArrayList<Monster> monsters = new ArrayList<>();
        //TODO: загрузка данных откуда-нибудь
        monsters.add(new Monster(1,R.drawable.strazhnik,R.string.quard_name, R.string.quard_desc));
        monsters.add(new Monster(2,R.drawable.sessia_sketch,R.string.session_name,R.string.session_desc));
        monsters.add(new Monster(3,R.drawable.logistic, R.string.logistic_name, R.string.logistic_desc));
        monsters.add(new Monster(4,R.drawable.neryakha_sketch,R.string.neryaha_name,R.string.neryaha_desc));
        monsters.add(new Monster(5,R.drawable.sociofob,R.string.sociofob_name,R.string.sociofob_desc));
        monsters.add(new Monster(6,R.drawable.golodny_sketch,R.string.hungry_name,R.string.hungry_desc));
        monsters.add(new Monster(7,R.drawable.byurokrat_sketch,R.string.burea_name,R.string.burea_desc));
        return monsters;
    }

    @Override
    public void update(Monster item) {
        //TODO: сохранение статуса монстра(прочитана ли о нем информации)
    }
}
