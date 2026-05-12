package uz.gita.testapp.data.repository;

import java.util.List;

import uz.gita.testapp.data.model.CategoryData;
import uz.gita.testapp.data.model.MyAnswerData;
import uz.gita.testapp.data.model.QuestionData;

public interface AppRepository {
    List<CategoryData> getCategories();
    List<QuestionData> getQuestionByLevel(Integer level);
    void setLevel(int level);
    int getLevel();
    void resetGame();

    int getMaxQuestionCount(Integer level);

    void myAnswer(List<MyAnswerData> list);
    List<MyAnswerData> getMyAnswer();
    void removeMyAnswer();
}
