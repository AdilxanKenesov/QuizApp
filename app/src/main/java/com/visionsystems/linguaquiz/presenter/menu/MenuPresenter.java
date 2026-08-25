package com.visionsystems.linguaquiz.presenter.menu;

import java.util.List;

import com.visionsystems.linguaquiz.data.model.CategoryData;

public class MenuPresenter implements MenuContract.Presenter{
    private final MenuContract.Model model;
    private final MenuContract.View view;

    public MenuPresenter(MenuContract.View view){
        this.view = view;
        this.model = MenuModel.getInstance();
        loadData();
    }


    @Override
    public void loadData() {
        List<CategoryData> list = model.getCategories();
        view.showCategories(list);

        int currentLevel = model.getCurrentLevel();
        int totalLevels = list.size();
        int completedLevels = currentLevel - 1;
        if (completedLevels < 0) completedLevels = 0;
        int percent = totalLevels > 0 ? (completedLevels * 100) / totalLevels : 0;
        String progressText = String.format("Progress: %d/%d (%d%%)", completedLevels+1, totalLevels, percent);
        view.showProgress(progressText);
        boolean hasSaved = model.hasSavedGame();
        view.setContinueButtonVisibility(hasSaved);

    }

    @Override
    public void clickCategory(CategoryData data) {
        if (data.isOpened()){
            view.navigateToQuiz(data.getLevel(), false);
        }else {
            view.showMessage("This level is locked");
        }
    }

    @Override
    public void clickContinue() {
        int savedLevelId = model.getSavedLevel();
        view.navigateToQuiz(savedLevelId, true);

    }
}
