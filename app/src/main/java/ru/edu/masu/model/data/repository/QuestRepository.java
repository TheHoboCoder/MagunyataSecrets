package ru.edu.masu.model.data.repository;

import java.util.ArrayList;
import java.util.List;

import ru.edu.masu.R;
import ru.edu.masu.model.CodeQuestPass;
import ru.edu.masu.model.data.entities.Hint;
import ru.edu.masu.model.data.entities.QuestItem;
import ru.edu.masu.model.data.entities.StoryItem;

public class QuestRepository implements IRepository<QuestItem> {

    @Override
    public List<QuestItem> getAll() {
        //TODO: загрузка данных
        ArrayList<QuestItem> questItems = new ArrayList<>();

        //первый квест
        QuestItem firstQuest = new QuestItem(R.drawable.kiski, "Первый квест");
        firstQuest.setTask("Помоги Стражу найти потерянный код!");
        firstQuest.setQuestProviderImg(R.drawable.strazhnik);
        firstQuest.setQuestHelperImg(R.drawable.sociofob);
        //способ сдачи квеста - ввод пароля
        firstQuest.setQuestPass(new CodeQuestPass("123"));
        //предыстория
        List<StoryItem> questStory = new ArrayList<>();
        questStory.add(new StoryItem(R.drawable.strazhnik, R.drawable.predystoria_kv1_1));
        questStory.add(new StoryItem(R.drawable.logistic, R.drawable.predystoria_kv1_2));
        questStory.add(new StoryItem(R.drawable.safe, R.drawable.predystoria_kv1_3));
        firstQuest.setQuestStory(questStory);
        //подсказки
        List<Hint> hints = new ArrayList<>();
        hints.add(new Hint("Описание", R.drawable.podskazka_logistik_kv1, 3000));
        hints.add(new Hint("Еще одна подсказка", R.drawable.podskazka_logistik_kv1, 4000));
        hints.add(new Hint("Последняя подсказка", R.drawable.podskazka_logistik_kv1, 5000));
        firstQuest.setQuestHints(hints);

        QuestItem testQuest = new QuestItem(R.drawable.kiski, "Квест-тест");
        testQuest.setTask("Тут написано задание квеста");
        testQuest.setQuestProviderImg(R.drawable.sessia_sketch);
        testQuest.setQuestHelperImg(R.drawable.golodny_sketch);
        //способ сдачи квеста - ввод пароля
        testQuest.setQuestPass(new CodeQuestPass("321"));
        testQuest.setQuestHints(hints);

        questItems.add(firstQuest);
        questItems.add(testQuest);

        for (int i = 2; i < 11; i++)  {
            questItems.add(new QuestItem(R.drawable.kiski, "Квест "+i));
        }
        return questItems;
    }

    @Override
    public void update(QuestItem item) {

    }
}
