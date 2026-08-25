package com.visionsystems.linguaquiz.presenter.result;

import java.util.List;

import com.visionsystems.linguaquiz.data.model.MyAnswerData;

public interface ResultContract {
    interface View {
        void showResults(List<MyAnswerData> list, int correctCount, int totalCount, int percentage);
        void navigateToHome();
        void navigateToQuiz(int levelId);
    }

    interface Presenter {
        void start(int currentLevelId);
        void onHomeClicked();
        void onRestartClicked();
    }

    interface Model {
        List<MyAnswerData> getMyAnswers();
        void setLevel(int levelId);
    }
}
