package com.visionsystems.linguaquiz.data.repository;

import java.util.List;
import java.util.Map;

import com.visionsystems.linguaquiz.data.model.CategoryData;
import com.visionsystems.linguaquiz.data.model.MyAnswerData;
import com.visionsystems.linguaquiz.data.model.QuestionData;

public interface AppRepository {
    List<CategoryData> getCategories();
    List<QuestionData> getQuestionByLevel(Integer level);
    void setLevel(int level);
    int getLevel();
    void resetGame();
    void myAnswer(List<MyAnswerData> list);
    List<MyAnswerData> getMyAnswer();
    void removeMyAnswer();

    void saveGameState(int levelId, int currentIndex, Map<Integer, String> userAnswers, Map<Integer, List<String>> shuffledOptions);
    String getGameState();
    void removeGameState();
    boolean isState();


}
