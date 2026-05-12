package uz.gita.testapp.presenter.menu;

import java.util.List;

import uz.gita.testapp.data.model.CategoryData;

public interface MenuContract {
    interface View {
        void showCategories(List<CategoryData> list);
        void navigateToQuiz(int categoryId);
        void showProgress(String progressText);
        void showMessage(String message);
    }

    interface Presenter {
        void loadData();
        void clickCategory(CategoryData data);
    }

    interface Model {
        List<CategoryData> getCategories();
        int getCurrentLevel();
    }
}
