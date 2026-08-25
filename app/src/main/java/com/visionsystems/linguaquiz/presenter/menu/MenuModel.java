package com.visionsystems.linguaquiz.presenter.menu;

import java.util.List;

import com.visionsystems.linguaquiz.data.model.CategoryData;
import com.visionsystems.linguaquiz.data.repository.AppRepository;
import com.visionsystems.linguaquiz.data.repository.AppRepositoryImpl;

public class MenuModel implements MenuContract.Model{
    private final AppRepository repository = AppRepositoryImpl.getInstance();
    private static MenuModel instance;
    private MenuModel(){}
    public static MenuModel getInstance(){
        if (instance == null){
            instance = new MenuModel();
        }
        return instance;
    }

    @Override
    public List<CategoryData> getCategories() {
        return repository.getCategories();
    }

    @Override
    public int getCurrentLevel() {
        return repository.getLevel();
    }

    @Override
    public int getSavedLevel() {
        String state = repository.getGameState();
        if (state != null) {
            try {
                return Integer.parseInt(state.split("\\|")[0]);
            } catch (Exception e) {
                return 1;
            }
        }
        return 1;
    }

    @Override
    public boolean hasSavedGame() {
        return repository.isState();
    }




}
