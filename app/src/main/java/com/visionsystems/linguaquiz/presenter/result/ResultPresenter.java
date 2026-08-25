package com.visionsystems.linguaquiz.presenter.result;

import java.util.List;

import com.visionsystems.linguaquiz.data.model.MyAnswerData;

public class ResultPresenter implements ResultContract.Presenter{
    private final ResultContract.View view;
    private final ResultContract.Model model;
    private int levelId;

    public ResultPresenter(ResultContract.View view) {
        this.view = view;
        this.model = new ResultModel();
    }

    @Override
    public void start(int currentLevelId) {
        List<MyAnswerData> answers = model.getMyAnswers();
        int correctCount = 0;

        for (MyAnswerData data : answers) {
            if (data.getRightAnswer().trim().equalsIgnoreCase(data.getWrongAnswer().trim())) {
                correctCount++;
            }
        }

        int totalCount = answers.size();
        int percentage = (totalCount == 0) ? 0 : (correctCount * 100) / totalCount;

        if (percentage >= 70) {
            model.setLevel(currentLevelId + 1);
        }
        levelId = currentLevelId;

        view.showResults(answers, correctCount, totalCount, percentage);
    }

    @Override
    public void onHomeClicked() {
        view.navigateToHome();
    }

    @Override
    public void onRestartClicked() {
        view.navigateToQuiz(levelId);
    }
}
