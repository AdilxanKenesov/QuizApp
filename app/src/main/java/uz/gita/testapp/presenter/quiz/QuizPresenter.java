package uz.gita.testapp.presenter.quiz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uz.gita.testapp.data.model.MyAnswerData;
import uz.gita.testapp.data.model.QuestionData;

public class QuizPresenter implements QuizContract.Presenter{
    private final QuizContract.Model model;
    private final QuizContract.View view;
    private List<QuestionData> questions;
    private final Map<Integer, String> userAnswers = new HashMap<>();
    private final Map<Integer, List<String>> shuffledOptionsMap = new HashMap<>();
    private int currentIndex = 0;
    private int levelId;

    public QuizPresenter(QuizContract.View view){
        this.model = QuizModel.getInstance();
        this.view = view;
    }


    @Override
    public void start(int level) {
        levelId = level;
        questions = model.getQuestionByLevel(level);
        for (int i = 0; i < questions.size(); i++) {
            shuffledOptionsMap.put(i, questions.get(i).getShuffledOptions());
        }
        loadCurrentQuestion();
    }
    private void loadCurrentQuestion(){
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
    }

    @Override
    public void next() {
        if (!userAnswers.containsKey(currentIndex)) {
            view.showToast("Please mark the answer!");
            return;
        }

        if (currentIndex < questions.size() - 1) {
            currentIndex++;
            loadCurrentQuestion();
        } else {
            saveAndFinish();
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
        List<MyAnswerData> results = new ArrayList<>();
        for (int i = 0; i < questions.size(); i++) {
            QuestionData q = questions.get(i);
            String userAnswer = userAnswers.get(i);

            results.add(new MyAnswerData(
                    levelId, i, q.getImage(), q.getQuestion(), q.getAnswer(), userAnswer
            ));
        }
        model.saveMyAnswer(results);
        view.navigateToResult();
    }
}
