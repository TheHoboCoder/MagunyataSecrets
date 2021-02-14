package ru.edu.masu.model.entities.questPass;

// интерфейс, в котором прописаны методы создания Verifier для каждого метода сдачи
public interface IQuestPassVerifierProvider {

    void createCodeQuestPass(CodeQuestPass codeQuestPass);

}
