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
        firstQuest.setQuestPass(new CodeQuestPass("123", CodeQuestPass.PassType.TEXT));
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
        testQuest.setQuestPass(new CodeQuestPass("321", CodeQuestPass.PassType.TEXT));
        testQuest.setQuestHints(hints);

        QuestItem qrQuest = new QuestItem(R.drawable.kiski, "Тест с QR");
        qrQuest.setTask("Тут написано задание квеста");
        qrQuest.setQuestProviderImg(R.drawable.logistic);
        qrQuest.setQuestHelperImg(R.drawable.kiski);
        qrQuest.setQuestPass(new CodeQuestPass("QR code quest!", CodeQuestPass.PassType.QR));
        qrQuest.setQuestHints(hints);

        questItems.add(firstQuest);
        questItems.add(testQuest);
        questItems.add(qrQuest);

        for (int i = 3; i <= 10; i++)  {
            QuestItem testItem = new QuestItem(R.drawable.kiski, "Квест "+i);
            testItem.setQuestHints(hints);
            testItem.setQuestProviderImg(R.drawable.golodny_sketch);
            testItem.setQuestHelperImg(R.drawable.logistic);
            testItem.setQuestPass(new CodeQuestPass("Квест "+ i, CodeQuestPass.PassType.TEXT));
            questItems.add(testItem);
        }
        return questItems;
    }

    @Override
    public void update(QuestItem item) {

    }
}
