package uz.gita.testapp.presenter.result;

import java.util.List;

import uz.gita.testapp.data.model.MyAnswerData;
import uz.gita.testapp.data.repository.AppRepositoryImpl;

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