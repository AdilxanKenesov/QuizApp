package com.visionsystems.linguaquiz.presenter.result;

import java.util.List;

import com.visionsystems.linguaquiz.data.model.MyAnswerData;
import com.visionsystems.linguaquiz.data.repository.AppRepositoryImpl;

public class ResultModel implements ResultContract.Model {
    @Override
    public List<MyAnswerData> getMyAnswers() {
        return AppRepositoryImpl.getInstance().getMyAnswer();
    }

    @Override
    public void setLevel(int levelId) {
        AppRepositoryImpl.getInstance().resetGame();
        AppRepositoryImpl.getInstance().setLevel(levelId);
    }

}