package com.visionsystems.linguaquiz.presenter.quiz;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.visionsystems.linguaquiz.data.model.MyAnswerData;
import com.visionsystems.linguaquiz.data.model.QuestionData;

public class QuizPresenter implements QuizContract.Presenter {
    private final QuizContract.Model model;
    private final QuizContract.View view;
    private List<QuestionData> questions;
    private final Map<Integer, String> userAnswers = new HashMap<>();
    private final Map<Integer, List<String>> shuffledOptionsMap = new HashMap<>();
    private int currentIndex = 0;
    private int levelId;
    private boolean isQuizFinished = false;

    public QuizPresenter(QuizContract.View view) {
        this.model = QuizModel.getInstance();
        this.view = view;
    }

    @Override
    public void start(int level) {
        this.levelId = level;
        questions = model.getQuestionByLevel(level);
        for (int i = 0; i < questions.size(); i++) {
            shuffledOptionsMap.put(i, questions.get(i).getShuffledOptions());
        }
        String savedState = model.getSavedState();
        if (savedState != null) {
            parseState(savedState);
            if (shuffledOptionsMap.isEmpty()) {
                generateNewShuffleOptions();
            }
        } else {
            generateNewShuffleOptions();
        }
        loadCurrentQuestion();
    }

    private void generateNewShuffleOptions() {
        shuffledOptionsMap.clear();
        for (int i = 0; i < questions.size(); i++) {
            shuffledOptionsMap.put(i, questions.get(i).getShuffledOptions());
        }
    }

    private void parseState(String state) {
        try {
            String[] parts = state.split("\\|");
            int savedLevel = Integer.parseInt(parts[0]);

            if (savedLevel == levelId) {
                this.currentIndex = Integer.parseInt(parts[1]);

                if (parts.length > 2 && !parts[2].isEmpty()) {
                    String[] answers = parts[2].split(",");
                    for (String answer : answers) {
                        String[] kv = answer.split(":");
                        if (kv.length == 2) userAnswers.put(Integer.parseInt(kv[0]), kv[1]);
                    }
                }

                if (parts.length > 3 && !parts[3].isEmpty()) {
                    String[] allOptions = parts[3].split(";");
                    for (String optionEntry : allOptions) {
                        String[] kv = optionEntry.split(":");
                        if (kv.length == 2) {
                            int qIdx = Integer.parseInt(kv[0]);
                            List<String> opts = new ArrayList<>(java.util.Arrays.asList(kv[1].split(",")));
                            shuffledOptionsMap.put(qIdx, opts);
                        }
                    }
                }
            }
        } catch (Exception e) {
            Log.e("TTT", Objects.requireNonNull(e.getMessage()));
        }
    }

    private void loadCurrentQuestion() {
        QuestionData q = questions.get(currentIndex);
        String previousAnswer = userAnswers.get(currentIndex);
        List<String> variants = shuffledOptionsMap.get(currentIndex);
        view.showQuestion(q, variants, currentIndex + 1, questions.size(), previousAnswer);
        view.updateHeader(levelId, currentIndex + 1, questions.size());
        view.setNavigationButtons(currentIndex == 0, currentIndex == questions.size() - 1);
    }

    @Override
    public void selectOption(String variant) {
        userAnswers.put(currentIndex, variant);
        saveCurrentState();
    }

    @Override
    public void next() {

        if (!userAnswers.containsKey(currentIndex)) {
            userAnswers.put(currentIndex, "Skipped");
        }

        saveCurrentState();

        if (currentIndex < questions.size() - 1) {
            currentIndex++;
            loadCurrentQuestion();
        } else {

            int firstSkipped = findNextSkippedIndex(0);
            if (firstSkipped != -1) {
                currentIndex = firstSkipped;
                loadCurrentQuestion();
                view.showToast("Please complete skipped questions");
            } else {
                saveAndFinish();
            }
        }
    }

    @Override
    public void prev() {
        if (currentIndex > 0) {
            currentIndex--;
            loadCurrentQuestion();
        }
    }

    private void saveAndFinish() {
        isQuizFinished = true;
        List<MyAnswerData> results = new ArrayList<>();
        for (int i = 0; i < questions.size(); i++) {
            QuestionData q = questions.get(i);
            String userAnswer = userAnswers.get(i);

            results.add(new MyAnswerData(
                    levelId, i, q.getImage(), q.getQuestion(), q.getAnswer(), userAnswer
            ));
        }
        model.saveMyAnswer(results);
        model.clearSavedState();
        view.navigateToResult();
    }

    @Override
    public void resetQuiz() {
        if (questions == null) {
            questions = model.getQuestionByLevel(levelId);
        }
        isQuizFinished = false;
        userAnswers.clear();

        currentIndex = 0;

        model.clearSavedState();

        generateNewShuffleOptions();
        loadCurrentQuestion();

        view.showToast("Reset Game");
    }

    @Override
    public void saveCurrentState() {
        if (isQuizFinished || userAnswers.isEmpty()) return;
        model.saveGameState(levelId, currentIndex, userAnswers, shuffledOptionsMap);
    }

    private int findNextSkippedIndex(int startIndex) {
        for (int i = startIndex; i < questions.size(); i++) {
            String answer = userAnswers.get(i);
            if (answer == null || "Skipped".equals(answer)) {
                return i;
            }
        }
        return -1;
    }
}