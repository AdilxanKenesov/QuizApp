package com.visionsystems.linguaquiz.presenter.menu;

import java.util.List;

import com.visionsystems.linguaquiz.data.model.CategoryData;

public interface MenuContract {
    interface View {
        void showCategories(List<CategoryData> list);
        void navigateToQuiz(int categoryId, boolean isContinue);
        void showProgress(String progressText);
        void showMessage(String message);
        void setContinueButtonVisibility(boolean isVisible);
    }

    interface Presenter {
        void loadData();
        void clickCategory(CategoryData data);
        void clickContinue();
    }

    interface Model {
        List<CategoryData> getCategories();
        int getCurrentLevel();
        int getSavedLevel();
        boolean hasSavedGame();

    }
}
