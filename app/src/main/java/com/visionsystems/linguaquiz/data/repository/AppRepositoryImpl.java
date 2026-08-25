package com.visionsystems.linguaquiz.data.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.visionsystems.linguaquiz.R;
import com.visionsystems.linguaquiz.data.local.LocalStroge;
import com.visionsystems.linguaquiz.data.model.CategoryData;
import com.visionsystems.linguaquiz.data.model.MyAnswerData;
import com.visionsystems.linguaquiz.data.model.QuestionData;

public class AppRepositoryImpl implements AppRepository{
    private static volatile AppRepositoryImpl instance;
    private final List<MyAnswerData> list  = new ArrayList<>();
    private final LocalStroge pref = LocalStroge.getInstance();

    private AppRepositoryImpl(){}
    public static void  init(){
        if (instance == null){
            synchronized (AppRepositoryImpl.class){
                if (instance == null){
                    instance = new AppRepositoryImpl();
                }
            }
        }
    }
    public static AppRepositoryImpl getInstance(){
        return instance;
    }

    @Override
    public List<CategoryData> getCategories() {
        return getCategoriess();
    }

    @Override
    public List<QuestionData> getQuestionByLevel(Integer level) {
        if (level != null){
            return getQuestions(level);
        }
        return Collections.emptyList();
    }

    @Override
    public void setLevel(int level) {
        pref.setLevel(level);
    }

    @Override
    public int getLevel() {
        return pref.getLevel();
    }

    @Override
    public void resetGame() {
        pref.removeLevel();

    }


    @Override
    public void myAnswer(List<MyAnswerData> list) {
        this.list.addAll(list);
    }

    @Override
    public List<MyAnswerData> getMyAnswer() {
        return list;
    }

    @Override
    public void removeMyAnswer() {
        list.clear();
    }

    @Override
    public void saveGameState(int levelId, int currentIndex, Map<Integer, String> userAnswers, Map<Integer, List<String>> shuffledOptions) {
        StringBuilder sb = new StringBuilder();
        sb.append(levelId).append("|").append(currentIndex).append("|");

        for (Map.Entry<Integer, String> entry : userAnswers.entrySet()) {
            sb.append(entry.getKey()).append(":").append(entry.getValue()).append(",");
        }
        if (!userAnswers.isEmpty()) sb.setLength(sb.length() - 1);

        sb.append("|");

        for (Map.Entry<Integer, List<String>> entry : shuffledOptions.entrySet()) {
            sb.append(entry.getKey()).append(":");
            for (String opt : entry.getValue()) {
                sb.append(opt).append(",");
            }
            sb.setLength(sb.length() - 1);
            sb.append(";");
        }
        if (!shuffledOptions.isEmpty()) sb.setLength(sb.length() - 1);
        pref.saveGameState(sb.toString());

    }

    @Override
    public String getGameState() {
        return pref.getGameState();
    }

    @Override
    public boolean isState() {
        return pref.getGameState() != null;
    }

    @Override
    public void removeGameState() {
        pref.removeGameState();
    }


    private  final HashMap<Integer, List<QuestionData>> questions = new HashMap<>();

    public  List<QuestionData> getQuestions(int level) {
        if (questions.isEmpty()) {
            loadQuestions();
        }
        return questions.get(level);
    }
    private  void loadQuestions() {

        List<QuestionData> fruitQuestions = new ArrayList<>();
        fruitQuestions.add(new QuestionData(1, "What is this fruit?", R.drawable.img, "Apple", "Apple", "Pear", "Peach", "Plum"));
        fruitQuestions.add(new QuestionData(1, "What is this fruit?", R.drawable.img_1, "Banana", "Lemon", "Banana", "Mango", "Kiwi"));
        fruitQuestions.add(new QuestionData(1, "Which berry is this?", R.drawable.img_2, "Strawberry", "Cherry", "Grape", "Strawberry", "Berry"));
        fruitQuestions.add(new QuestionData(1, "What is the English name for 'Uzum'?", R.drawable.img_3, "Grapes", "Grapes", "Orange", "Apple", "Melon"));
        fruitQuestions.add(new QuestionData(1, "This fruit is sour and yellow:", R.drawable.img_4, "Lemon", "Orange", "Lemon", "Pineapple", "Banana"));
        questions.put(1, fruitQuestions);

        List<QuestionData> animalQuestions = new ArrayList<>();
        animalQuestions.add(new QuestionData(2, "The king of the jungle is...", R.drawable.img_5, "Lion", "Tiger", "Lion", "Wolf", "Bear"));
        animalQuestions.add(new QuestionData(2, "Which animal has a long neck?", R.drawable.img_6, "Giraffe", "Elephant", "Horse", "Giraffe", "Zebra"));
        animalQuestions.add(new QuestionData(2, "It loves carrots:", R.drawable.img_7, "Rabbit", "Cat", "Dog", "Rabbit", "Mouse"));
        animalQuestions.add(new QuestionData(2, "The largest land animal:", R.drawable.img_8, "Elephant", "Hippo", "Elephant", "Rhino", "Lion"));
        animalQuestions.add(new QuestionData(2, "Man's best friend:", R.drawable.img_9, "Dog", "Cat", "Dog", "Bird", "Cow"));
        questions.put(2, animalQuestions);

        List<QuestionData> homeQuestions = new ArrayList<>();
        homeQuestions.add(new QuestionData(3, "Where do you sleep?", R.drawable.img_10, "Bed", "Chair", "Bed", "Table", "Sofa"));
        homeQuestions.add(new QuestionData(3, "We keep food cold in the...", R.drawable.img_11, "Fridge", "Oven", "Fridge", "Cabinet", "Sink"));
        homeQuestions.add(new QuestionData(3, "You sit on this to work:", R.drawable.img_12, "Chair", "Bed", "Chair", "Door", "Window"));
        homeQuestions.add(new QuestionData(3, "You watch news on...", R.drawable.img_13, "TV", "Radio", "Phone", "TV", "Clock"));
        homeQuestions.add(new QuestionData(3, "Where do you cook?", R.drawable.img_14, "Kitchen", "Bedroom", "Kitchen", "Garden", "Bathroom"));
        questions.put(3, homeQuestions);

        List<QuestionData> familyQuestions = new ArrayList<>();
        familyQuestions.add(new QuestionData(4, "Your mother's husband is your...", R.drawable.img_15, "Father", "Uncle", "Brother", "Father", "Grandpa"));
        familyQuestions.add(new QuestionData(4, "Your father's daughter is your...", R.drawable.img_16, "Sister", "Mother", "Sister", "Aunt", "Cousin"));
        familyQuestions.add(new QuestionData(4, "A very young child is a...", R.drawable.img_17, "Baby", "Man", "Baby", "Woman", "Boy"));
        familyQuestions.add(new QuestionData(4, "Father of your mother:", R.drawable.img_18, "Grandfather", "Brother", "Grandfather", "Father", "Son"));
        familyQuestions.add(new QuestionData(4, "Female parent:", R.drawable.img_19, "Mother", "Sister", "Aunt", "Mother", "Daughter"));
        questions.put(4, familyQuestions);

        List<QuestionData> colorQuestions = new ArrayList<>();
        colorQuestions.add(new QuestionData(5, "What color is the sky?", R.drawable.img_20, "Blue", "Red", "Blue", "Green", "Yellow"));
        colorQuestions.add(new QuestionData(5, "What color is the grass?", R.drawable.img_21, "Green", "Green", "Black", "White", "Pink"));
        colorQuestions.add(new QuestionData(5, "Sun is usually...", R.drawable.img_22, "Yellow", "Purple", "Orange", "Yellow", "Blue"));
        colorQuestions.add(new QuestionData(5, "The color of a tomato:", R.drawable.img_23, "Red", "Blue", "Red", "Brown", "Grey"));
        colorQuestions.add(new QuestionData(5, "Mix Red and White to get...", R.drawable.img_24, "Pink", "Pink", "Black", "Green", "Purple"));
        questions.put(5, colorQuestions);

        List<QuestionData> numberQuestions = new ArrayList<>();
        numberQuestions.add(new QuestionData(6, "How many fingers on one hand?", R.drawable.img_25, "Five", "Four", "Five", "Six", "Ten"));
        numberQuestions.add(new QuestionData(6, "2 + 2 = ?", R.drawable.img_26, "Four", "Three", "Four", "Five", "Two"));
        numberQuestions.add(new QuestionData(6, "How many legs does a spider have?", R.drawable.img_27, "Eight", "Six", "Seven", "Eight", "Nine"));
        numberQuestions.add(new QuestionData(6, "First number in counting:", R.drawable.img_28, "One", "Zero", "One", "Two", "Three"));
        numberQuestions.add(new QuestionData(6, "The number after nine:", R.drawable.img_29, "Ten", "Eight", "Nine", "Ten", "Eleven"));
        questions.put(6, numberQuestions);

        List<QuestionData> weatherQuestions = new ArrayList<>();
        weatherQuestions.add(new QuestionData(7, "When the sun shines, it is...", R.drawable.img_30, "Sunny", "Rainy", "Sunny", "Cloudy", "Snowy"));
        weatherQuestions.add(new QuestionData(7, "Water falling from clouds:", R.drawable.img_31, "Rain", "Snow", "Wind", "Rain", "Ice"));
        weatherQuestions.add(new QuestionData(7, "White and cold weather:", R.drawable.img_32, "Snowy", "Hot", "Snowy", "Dry", "Wet"));
        weatherQuestions.add(new QuestionData(7, "Strong air moving:", R.drawable.img_33, "Windy", "Stormy", "Windy", "Sunny", "Clear"));
        weatherQuestions.add(new QuestionData(7, "Grey sky with no sun:", R.drawable.img_34, "Cloudy", "Bright", "Dark", "Cloudy", "Stormy"));
        questions.put(7, weatherQuestions);

        List<QuestionData> foodQuestions = new ArrayList<>();
        foodQuestions.add(new QuestionData(8, "Italian food with cheese:", R.drawable.img_35, "Pizza", "Soup", "Salad", "Pizza", "Pasta"));
        foodQuestions.add(new QuestionData(8, "Morning meal is...", R.drawable.img_36, "Breakfast", "Lunch", "Dinner", "Breakfast", "Snack"));
        foodQuestions.add(new QuestionData(8, "Chicken produces...", R.drawable.img_37, "Egg", "Milk", "Egg", "Meat", "Bread"));
        foodQuestions.add(new QuestionData(8, "Sweet cold dessert:", R.drawable.img_38, "Ice cream", "Cake", "Ice cream", "Cookie", "Candy"));
        foodQuestions.add(new QuestionData(8, "Liquid food in a bowl:", R.drawable.img_39, "Soup", "Pizza", "Soup", "Rice", "Meat"));
        questions.put(8, foodQuestions);

        List<QuestionData> clothesQuestions = new ArrayList<>();
        clothesQuestions.add(new QuestionData(9, "You wear these on your feet:", R.drawable.img_40, "Shoes", "Socks", "Shoes", "Gloves", "Hats"));
        clothesQuestions.add(new QuestionData(9, "To protect your head:", R.drawable.img_41, "Hat", "Hat", "Shirt", "Belt", "Scarf"));
        clothesQuestions.add(new QuestionData(9, "Women often wear a...", R.drawable.img_42, "Dress", "Pants", "Tie", "Dress", "Suit"));
        clothesQuestions.add(new QuestionData(9, "When it is cold, wear a...", R.drawable.img_43, "Coat", "Shorts", "Coat", "T-shirt", "Cap"));
        clothesQuestions.add(new QuestionData(9, "Worn around the neck:", R.drawable.img_44, "Scarf", "Scarf", "Ring", "Watch", "Socks"));
        questions.put(9, clothesQuestions);

        List<QuestionData> convQuestions = new ArrayList<>();
        convQuestions.add(new QuestionData(10, "When you meet someone:", R.drawable.img_45, "Hello", "Bye", "Hello", "Sorry", "Please"));
        convQuestions.add(new QuestionData(10, "When you get a gift, say:", R.drawable.img_46, "Thank you", "No", "Thank you", "Wait", "Hello"));
        convQuestions.add(new QuestionData(10, "When leaving, say:", R.drawable.img_47, "Goodbye", "Goodbye", "Welcome", "Hi", "Thanks"));
        convQuestions.add(new QuestionData(10, "Ask for something politely:", R.drawable.img_48, "Please", "Give me", "Please", "Stop", "Go"));
        convQuestions.add(new QuestionData(10, "If you make a mistake:", R.drawable.img_49, "Sorry", "Yes", "Sorry", "Maybe", "Great"));
        questions.put(10, convQuestions);
    }
    private  final List<CategoryData> categories = new ArrayList<>();
    private  List<CategoryData> getCategoriess() {
        loadCategories();
        return categories;
    }

    private void loadCategories() {
        categories.clear();

        int currentLevel = getLevel();

        categories.add(new CategoryData(1, "Fruits", R.drawable.fruits, 0xFFFF6B6B, 0xFFFF8E53, 1 <= currentLevel));
        categories.add(new CategoryData(2, "Animals", R.drawable.animals, 0xFF4ECDC4, 0xFF44A08D, 2 <= currentLevel));
        categories.add(new CategoryData(3, "Home", R.drawable.chair, 0xFF667EEA, 0xFF764BA2, 3 <= currentLevel));
        categories.add(new CategoryData(4, "Family", R.drawable.family, 0xFFF093FB, 0xFFF5576C, 4 <= currentLevel));
        categories.add(new CategoryData(5, "Colors", R.drawable.colors, 0xFFFA709A, 0xFFFEE140, 5 <= currentLevel));
        categories.add(new CategoryData(6, "Numbers", R.drawable.numbers, 0xFF30CFD0, 0xFF330867, 6 <= currentLevel));
        categories.add(new CategoryData(7, "Weather", R.drawable.weather, 0xFF4FACFE, 0xFF00F2FE, 7 <= currentLevel));
        categories.add(new CategoryData(8, "Food", R.drawable.food, 0xFFFF9A56, 0xFFFF6A88, 8 <= currentLevel));
        categories.add(new CategoryData(9, "Clothes", R.drawable.clothes, 0xFF6A11CB, 0xFF2575FC, 9 <= currentLevel));
        categories.add(new CategoryData(10, "Conversation", R.drawable.conversation, 0xFFFDC830, 0xFFF37335, 10 <= currentLevel));
    }
}
