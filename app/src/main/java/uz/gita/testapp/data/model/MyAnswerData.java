package uz.gita.testapp.data.model;

public class MyAnswerData {
    private int level;
    private int index;
    private String image;
    private String question;
    private String rightAnswer;
    private String wrongAnswer;

    public MyAnswerData(int level, int index, String image, String question, String rightAnswer, String wrongAnswer){
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


    public String getImage() {
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
