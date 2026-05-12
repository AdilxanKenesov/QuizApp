package uz.gita.testapp.data.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuestionData {
    private int level;
    private String question;
    private String image;
    private String answer;
    private String variant1;
    private String variant2;
    private String variant3;
    private String variant4;

    public QuestionData(int level, String question,String image, String answer, String variant1, String variant2, String variant3, String variant4){
        this.level = level;
        this.question = question;
        this.image = image;
        this.answer = answer;
        this.variant1 = variant1;
        this.variant2 = variant2;
        this.variant3 = variant3;
        this.variant4 = variant4;

    }


    public int getLevel() {
        return level;
    }
    public void setLevel(int level) {
        this.level = level;
    }
    public String getQuestion() {
        return question;
    }
    public void setQuestion(String question) {
        this.question = question;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getAnswer() {
        return answer;
    }
    public void setAnswer(String answer) {
        this.answer = answer;
    }
    public String getVariant1() {
        return variant1;
    }
    public void setVariant1(String variant1) {
        this.variant1 = variant1;
    }
    public String getVariant2() {
        return variant2;
    }
    public void setVariant2(String variant2) {
        this.variant2 = variant2;
    }
    public String getVariant3() {
        return variant3;
    }
    public void setVariant3(String variant3) {
        this.variant3 = variant3;
    }
    public String getVariant4() {
        return variant4;
    }
    public void setVariant4(String variant4) {
        this.variant4 = variant4;
    }

    public List<String> getShuffledOptions() {
        List<String> options = new ArrayList<>();
        options.add(variant1);
        options.add(variant2);
        options.add(variant3);
        options.add(variant4);
        Collections.shuffle(options);
        return options;
    }

}

// Fruite Animals Home Family Colors Numbers Weather Food Clothes Conversation