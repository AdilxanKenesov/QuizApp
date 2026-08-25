package com.visionsystems.linguaquiz.presenter.quiz;

import java.util.List;
import java.util.Map;

import com.visionsystems.linguaquiz.data.model.MyAnswerData;
import com.visionsystems.linguaquiz.data.model.QuestionData;

public interface QuizContract {
    interface View {
        void showQuestion(QuestionData data, List<String> shuffledOptions, int position, int total, String selectedAnswer);
        void updateHeader(int level, int position, int total);
        void setNavigationButtons(boolean isFirst, boolean isLast);
        void showToast(String message);
        void navigateToResult();
        void finishQuiz();
    }

    interface Presenter {
        void start(int level);
        void selectOption(String variant);
        void next();
        void prev();
        void resetQuiz();
        void saveCurrentState();
    }

    interface Model {
        List<QuestionData> getQuestionByLevel(Integer level);
        void saveMyAnswer(List<MyAnswerData> list);
        void saveGameState(int levelId, int currentIndex, Map<Integer, String> userAnswers, Map<Integer, List<String>> shuffledOptions);
        String getSavedState();
        void clearSavedState();
    }
}