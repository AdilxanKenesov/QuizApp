package uz.gita.testapp.presenter.quiz;


import java.util.List;

import uz.gita.testapp.data.model.MyAnswerData;
import uz.gita.testapp.data.model.QuestionData;
import uz.gita.testapp.data.repository.AppRepository;
import uz.gita.testapp.data.repository.AppRepositoryImpl;

public class QuizModel implements QuizContract.Model{
    private static QuizModel instance;
    private AppRepository repository = AppRepositoryImpl.getInstance();
    private QuizModel(){}
    public static QuizModel getInstance(){
        if (instance == null){
            instance = new QuizModel();
        }
        return instance;
    }


    @Override
    public List<QuestionData> getQuestionByLevel(Integer level) {
        return repository.getQuestionByLevel(level);
    }

    @Override
    public void saveMyAnswer(List<MyAnswerData> list) {
        repository.removeMyAnswer();
        repository.myAnswer(list);
    }
}
