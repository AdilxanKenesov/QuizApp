package uz.gita.testapp.data.local;

import android.content.Context;
import android.content.SharedPreferences;

public class LocalStroge {
    private static LocalStroge instance;
    private static SharedPreferences sharedPreferences;
    private static final String NAME_KEY = "quiz_app";

    private LocalStroge(){}

    public static void init(Context context){
        if (instance == null){
            synchronized (LocalStroge.class){
                if (instance == null){
                    instance = new LocalStroge();
                    sharedPreferences = context.getSharedPreferences(NAME_KEY, Context.MODE_PRIVATE);
                }
            }
        }
    }
    public static LocalStroge getInstance(){
        return instance;
    }

    public void setLevel(int level){
        sharedPreferences.edit().putInt("level", level).apply();
    }
    public int getLevel(){
        return sharedPreferences.getInt("level", 1);
    }
    public void removeLevel(){
        sharedPreferences.edit().remove("level").apply();
    }

    public void saveGameState(String state){
        sharedPreferences.edit().putString("state", state).apply();
    }

    public String getGameState(){
        return sharedPreferences.getString("state", null);
    }
    public void removeGameState(){
        sharedPreferences.edit().remove("state").apply();
    }

}
