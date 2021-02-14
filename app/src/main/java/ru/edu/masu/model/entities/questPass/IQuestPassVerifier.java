package ru.edu.masu.model.entities.questPass;

import ru.edu.masu.model.entities.questPass.IQuestPass;

// интерфейс для классов, которые работают с конкретным методом сдачи квеста IQuestPass
// они знают, как проверить сдачу квеста
public interface IQuestPassVerifier {
    // если квест был сдан, то класс должен вызвать этот метод
    void setPassed(IQuestPass questPass);
}
