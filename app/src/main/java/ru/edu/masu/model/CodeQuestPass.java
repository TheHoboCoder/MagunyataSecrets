package ru.edu.masu.model;

import java.util.ArrayList;
import java.util.List;

public class CodeQuestPass implements IQuestPass {

    private List<IQuestFinished> iQuestFinishedCallbacks;
    private String passCode;

    public CodeQuestPass(String passCode){
        this.passCode = passCode;
        iQuestFinishedCallbacks = new ArrayList<>();
    }

    public void enterCode(String code){
        boolean finished = passCode.equals(code);
        for(IQuestFinished callback:iQuestFinishedCallbacks){
            if(finished){
                callback.onFinish();
            }
            else{
                callback.onPassFailed();
            }
        }
    }

    @Override
    public void addCallback(IQuestFinished iQuestFinished) {
        this.iQuestFinishedCallbacks.add(iQuestFinished);
    }
}
