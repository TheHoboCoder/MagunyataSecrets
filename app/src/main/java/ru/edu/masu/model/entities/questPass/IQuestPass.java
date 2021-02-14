package ru.edu.masu.model.entities.questPass;

// Интерфейс, описывающий способ сдачи квеста
public interface IQuestPass{

    // так как разные способы сдачи квеста требуют разной логики проверки,
    // то мы не можем определить конкретный метод типа boolean check(params_to_check)
    // вместо этого просто делаем метод для проверки статуса сдачи
    boolean isPassed();

    String getName();
    void setName(String name);

    void from(IQuestPass questPass);

    // тут опять используем паттерн посетитель (см. IEquipment)
    // чтобы не привязываться к конкретной платформе, которая выполняет наши квесты, мы
    // создаем интерфейс IQuestPassVerifierProvider, в котором описываем методы создания
    // Verifier для конкретного способа сдачи
    void createVerifier(IQuestPassVerifierProvider verifierProvider);
}
