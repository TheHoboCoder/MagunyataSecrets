package ru.edu.masu.model.entities.questPass;

// метод сдачи квеста при помощи кода
// код нужно либо вводить, либо сканировать QR с этим кодом (PassType)
public class CodeQuestPass extends BaseQuestPass {

    public void setPassCode(String passCode) {
        this.passCode = passCode;
    }

    public void setPassType(PassType passType) {
        this.passType = passType;
    }

    private String passCode;

    public enum PassType{
        TEXT,
        QR
    }

    public PassType getPassType() {
        return passType;
    }

    private PassType passType;

    public CodeQuestPass(){ }

    public CodeQuestPass(String passCode, PassType passType){
        this.passCode = passCode;
        this.passType = passType;
    }


    public void enterCode(String code){
        isPassed = passCode.equals(code);
    }

    @Override
    public boolean isPassed() {
        return isPassed;
    }

    @Override
    public void from(IQuestPass questPass) {
        if(questPass instanceof CodeQuestPass){
            CodeQuestPass newInstance = (CodeQuestPass) questPass;
            if(newInstance.passCode.equals(this.passCode)
                && newInstance.getPassType() == passType){
                this.isPassed = newInstance.isPassed;
            }
        }
    }

    @Override
    public void createVerifier(IQuestPassVerifierProvider verifierProvider) {
        verifierProvider.createCodeQuestPass(this);
    }

}
