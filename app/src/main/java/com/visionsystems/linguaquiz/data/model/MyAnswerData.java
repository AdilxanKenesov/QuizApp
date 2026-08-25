package com.visionsystems.linguaquiz.data.model;

public class MyAnswerData {
    private int level;
    private int index;
    private int image;
    private String question;
    private String rightAnswer;
    private String wrongAnswer;

    public MyAnswerData(int level, int index, int image, String question, String rightAnswer, String wrongAnswer){
        this.level = level;
        this.index = index;
        this.image = image;
        this.question = question;
        this.rightAnswer = rightAnswer;
        this.wrongAnswer = wrongAnswer;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }


    public int getImage() {
        return image;
    }

    public String getQuestion() {
        return question;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    public String getWrongAnswer() {
        return wrongAnswer;
    }



}
