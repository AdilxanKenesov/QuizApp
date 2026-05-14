package uz.gita.testapp.data.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uz.gita.testapp.R;
import uz.gita.testapp.data.local.LocalStroge;
import uz.gita.testapp.data.model.CategoryData;
import uz.gita.testapp.data.model.MyAnswerData;
import uz.gita.testapp.data.model.QuestionData;

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
        fruitQuestions.add(new QuestionData(1, "What is this fruit?", "https://www.pngarts.com/files/12/Red-Apple-Fruit-PNG-Image.png", "Apple", "Apple", "Pear", "Peach", "Plum"));
        fruitQuestions.add(new QuestionData(1, "What is this fruit?", "https://www.pngall.com/wp-content/uploads/2016/04/Banana-Free-Download-PNG.png", "Banana", "Lemon", "Banana", "Mango", "Kiwi"));
        fruitQuestions.add(new QuestionData(1, "Which berry is this?", "https://pluspng.com/img-png/strawberry-transparent-png-600.png", "Strawberry", "Cherry", "Grape", "Strawberry", "Berry"));
        fruitQuestions.add(new QuestionData(1, "What is the English name for 'Uzum'?", "https://static.vecteezy.com/system/resources/thumbnails/027/125/716/small/grapes-isolated-on-transparent-background-grape-clip-art-generative-ai-png.png", "Grapes", "Grapes", "Orange", "Apple", "Melon"));
        fruitQuestions.add(new QuestionData(1, "This fruit is sour and yellow:", "https://www.freepnglogos.com/uploads/lemon-png/lemon-juice-sampar-drinking-water-19.png", "Lemon", "Orange", "Lemon", "Pineapple", "Banana"));
        questions.put(1, fruitQuestions);

        List<QuestionData> animalQuestions = new ArrayList<>();
        animalQuestions.add(new QuestionData(2, "The king of the jungle is...", "https://static.vecteezy.com/system/resources/thumbnails/026/576/100/small/lion-animal-isolated-png.png", "Lion", "Tiger", "Lion", "Wolf", "Bear"));
        animalQuestions.add(new QuestionData(2, "Which animal has a long neck?", "https://static.vecteezy.com/system/resources/thumbnails/016/761/888/small/animal-giraffe-isolated-png.png", "Giraffe", "Elephant", "Horse", "Giraffe", "Zebra"));
        animalQuestions.add(new QuestionData(2, "It loves carrots:", "https://static.vecteezy.com/system/resources/thumbnails/016/457/777/small/realistic-computer-drawing-of-a-rabbit-png.png", "Rabbit", "Cat", "Dog", "Rabbit", "Mouse"));
        animalQuestions.add(new QuestionData(2, "The largest land animal:", "https://www.pngarts.com/files/4/Elephant-Transparent-Image.png", "Elephant", "Hippo", "Elephant", "Rhino", "Lion"));
        animalQuestions.add(new QuestionData(2, "Man's best friend:", "https://static.vecteezy.com/system/resources/thumbnails/044/279/923/small/dog-on-isolated-background-png.png", "Dog", "Cat", "Dog", "Bird", "Cow"));
        questions.put(2, animalQuestions);

        List<QuestionData> homeQuestions = new ArrayList<>();
        homeQuestions.add(new QuestionData(3, "Where do you sleep?", "https://pngimg.com/uploads/bed/bed_PNG17418.png", "Bed", "Chair", "Bed", "Table", "Sofa"));
        homeQuestions.add(new QuestionData(3, "We keep food cold in the...", "https://png.pngtree.com/png-vector/20240104/ourmid/pngtree-stainless-steel-open-fridge-png-image_11402215.png", "Fridge", "Oven", "Fridge", "Cabinet", "Sink"));
        homeQuestions.add(new QuestionData(3, "You sit on this to work:", "https://freepngimg.com/thumb/chair/1-chair-png-image.png", "Chair", "Bed", "Chair", "Door", "Window"));
        homeQuestions.add(new QuestionData(3, "You watch news on...", "https://png.pngtree.com/png-vector/20230408/ourmid/pngtree-led-tv-television-screen-vector-png-image_6673700.png", "TV", "Radio", "Phone", "TV", "Clock"));
        homeQuestions.add(new QuestionData(3, "Where do you cook?", "https://www.pngall.com/wp-content/uploads/8/Kitchen-PNG-File.png", "Kitchen", "Bedroom", "Kitchen", "Garden", "Bathroom"));
        questions.put(3, homeQuestions);

        List<QuestionData> familyQuestions = new ArrayList<>();
        familyQuestions.add(new QuestionData(4, "Your mother's husband is your...", "https://www.pngall.com/wp-content/uploads/15/Dad-PNG-Photo.png", "Father", "Uncle", "Brother", "Father", "Grandpa"));
        familyQuestions.add(new QuestionData(4, "Your father's daughter is your...", "https://static.vecteezy.com/system/resources/thumbnails/047/824/766/small/cheerful-girls-embracing-each-other-free-png.png", "Sister", "Mother", "Sister", "Aunt", "Cousin"));
        familyQuestions.add(new QuestionData(4, "A very young child is a...", "https://static.vecteezy.com/system/resources/thumbnails/057/852/099/small/happy-baby-playing-on-soft-white-surface-covered-with-blue-towel-smiling-with-joy-and-excitement-during-a-cozy-afternoon-free-png.png", "Baby", "Man", "Baby", "Woman", "Boy"));
        familyQuestions.add(new QuestionData(4, "Father of your mother:", "https://clipart-library.com/newhp/grandfather-clipart-2.png", "Grandfather", "Brother", "Grandfather", "Father", "Son"));
        familyQuestions.add(new QuestionData(4, "Female parent:", "https://static.vecteezy.com/system/resources/thumbnails/041/493/765/small/ai-generated-portrait-of-a-smiling-mother-and-baby-free-png.png", "Mother", "Sister", "Aunt", "Mother", "Daughter"));
        questions.put(4, familyQuestions);

        List<QuestionData> colorQuestions = new ArrayList<>();
        colorQuestions.add(new QuestionData(5, "What color is the sky?", "https://img.url/sky.png", "Blue", "Red", "Blue", "Green", "Yellow"));
        colorQuestions.add(new QuestionData(5, "What color is the grass?", "https://img.url/grass.png", "Green", "Green", "Black", "White", "Pink"));
        colorQuestions.add(new QuestionData(5, "Sun is usually...", "https://img.url/sun.png", "Yellow", "Purple", "Orange", "Yellow", "Blue"));
        colorQuestions.add(new QuestionData(5, "The color of a tomato:", "https://img.url/tomato.png", "Red", "Blue", "Red", "Brown", "Grey"));
        colorQuestions.add(new QuestionData(5, "Mix Red and White to get...", "https://img.url/pink.png", "Pink", "Pink", "Black", "Green", "Purple"));
        questions.put(5, colorQuestions);

        List<QuestionData> numberQuestions = new ArrayList<>();
        numberQuestions.add(new QuestionData(6, "How many fingers on one hand?", "https://img.url/hand.png", "Five", "Four", "Five", "Six", "Ten"));
        numberQuestions.add(new QuestionData(6, "2 + 2 = ?", "https://img.url/four.png", "Four", "Three", "Four", "Five", "Two"));
        numberQuestions.add(new QuestionData(6, "How many legs does a spider have?", "https://img.url/spider.png", "Eight", "Six", "Seven", "Eight", "Nine"));
        numberQuestions.add(new QuestionData(6, "First number in counting:", "https://img.url/one.png", "One", "Zero", "One", "Two", "Three"));
        numberQuestions.add(new QuestionData(6, "The number after nine:", "https://img.url/ten.png", "Ten", "Eight", "Nine", "Ten", "Eleven"));
        questions.put(6, numberQuestions);

        List<QuestionData> weatherQuestions = new ArrayList<>();
        weatherQuestions.add(new QuestionData(7, "When the sun shines, it is...", "https://img.url/sunny.png", "Sunny", "Rainy", "Sunny", "Cloudy", "Snowy"));
        weatherQuestions.add(new QuestionData(7, "Water falling from clouds:", "https://img.url/rainy.png", "Rain", "Snow", "Wind", "Rain", "Ice"));
        weatherQuestions.add(new QuestionData(7, "White and cold weather:", "https://img.url/snowy.png", "Snowy", "Hot", "Snowy", "Dry", "Wet"));
        weatherQuestions.add(new QuestionData(7, "Strong air moving:", "https://img.url/windy.png", "Windy", "Stormy", "Windy", "Sunny", "Clear"));
        weatherQuestions.add(new QuestionData(7, "Grey sky with no sun:", "https://img.url/cloudy.png", "Cloudy", "Bright", "Dark", "Cloudy", "Stormy"));
        questions.put(7, weatherQuestions);

        List<QuestionData> foodQuestions = new ArrayList<>();
        foodQuestions.add(new QuestionData(8, "Italian food with cheese:", "https://img.url/pizza.png", "Pizza", "Soup", "Salad", "Pizza", "Pasta"));
        foodQuestions.add(new QuestionData(8, "Morning meal is...", "https://img.url/breakfast.png", "Breakfast", "Lunch", "Dinner", "Breakfast", "Snack"));
        foodQuestions.add(new QuestionData(8, "Chicken produces...", "https://img.url/egg.png", "Egg", "Milk", "Egg", "Meat", "Bread"));
        foodQuestions.add(new QuestionData(8, "Sweet cold dessert:", "https://img.url/icecream.png", "Ice cream", "Cake", "Ice cream", "Cookie", "Candy"));
        foodQuestions.add(new QuestionData(8, "Liquid food in a bowl:", "https://img.url/soup.png", "Soup", "Pizza", "Soup", "Rice", "Meat"));
        questions.put(8, foodQuestions);

        List<QuestionData> clothesQuestions = new ArrayList<>();
        clothesQuestions.add(new QuestionData(9, "You wear these on your feet:", "https://img.url/shoes.png", "Shoes", "Socks", "Shoes", "Gloves", "Hats"));
        clothesQuestions.add(new QuestionData(9, "To protect your head:", "https://img.url/hat.png", "Hat", "Hat", "Shirt", "Belt", "Scarf"));
        clothesQuestions.add(new QuestionData(9, "Women often wear a...", "https://img.url/dress.png", "Dress", "Pants", "Tie", "Dress", "Suit"));
        clothesQuestions.add(new QuestionData(9, "When it is cold, wear a...", "https://img.url/coat.png", "Coat", "Shorts", "Coat", "T-shirt", "Cap"));
        clothesQuestions.add(new QuestionData(9, "Worn around the neck:", "https://img.url/scarf.png", "Scarf", "Scarf", "Ring", "Watch", "Socks"));
        questions.put(9, clothesQuestions);

        List<QuestionData> convQuestions = new ArrayList<>();
        convQuestions.add(new QuestionData(10, "When you meet someone:", "https://img.url/hello.png", "Hello", "Bye", "Hello", "Sorry", "Please"));
        convQuestions.add(new QuestionData(10, "When you get a gift, say:", "https://img.url/thanks.png", "Thank you", "No", "Thank you", "Wait", "Hello"));
        convQuestions.add(new QuestionData(10, "When leaving, say:", "https://img.url/bye.png", "Goodbye", "Goodbye", "Welcome", "Hi", "Thanks"));
        convQuestions.add(new QuestionData(10, "Ask for something politely:", "https://img.url/please.png", "Please", "Give me", "Please", "Stop", "Go"));
        convQuestions.add(new QuestionData(10, "If you make a mistake:", "https://img.url/sorry.png", "Sorry", "Yes", "Sorry", "Maybe", "Great"));
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
