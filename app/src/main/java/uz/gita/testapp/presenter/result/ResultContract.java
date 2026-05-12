package uz.gita.testapp.presenter.result;

import java.util.List;

import uz.gita.testapp.data.model.MyAnswerData;

public interface ResultContract {
    interface View {
        void showResults(List<MyAnswerData> list, int correctCount, int totalCount, int percentage);
        void navigateToHome();
    }

    interface Presenter {
        void start(int currentLevelId);
        void onHomeClicked();
    }

    interface Model {
        List<MyAnswerData> getMyAnswers();
        void setLevel(int levelId);
    }
}
