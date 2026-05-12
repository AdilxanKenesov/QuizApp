package uz.gita.testapp.presenter.menu;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.List;
import uz.gita.testapp.R;
import uz.gita.testapp.data.model.CategoryData;
import uz.gita.testapp.presenter.quiz.QuizActivity;

public class MenuActivity extends AppCompatActivity implements MenuContract.View {
    private MenuContract.Presenter presenter;
    private TextView tvProgress;
    private LinearLayout categoryContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        loadView();

    }

    private void loadView(){
        tvProgress = findViewById(R.id.tvProgress);
        categoryContainer = findViewById(R.id.category_container);
        presenter = new MenuPresenter(this);
    }


    @Override
    public void showCategories(List<CategoryData> list) {
        categoryContainer.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(this);
        LinearLayout currentRow = null;
        for (int i = 0; i < list.size(); i++) {
            CategoryData data = list.get(i);
            if (i%2 == 0){
                currentRow = (LinearLayout) inflater.inflate(R.layout.item_container_category, categoryContainer, false);
                categoryContainer.addView(currentRow);
            }

            View cardView = inflater.inflate(R.layout.item_category_cart, currentRow, false);
            bindDataToCard(cardView, data, i);

            cardView.setOnClickListener(v-> presenter.clickCategory(data));
            if (currentRow != null){
                currentRow.addView(cardView);
            }
        }
    }
    @SuppressLint("SetTextI18n")
    private void bindDataToCard(View cardView, CategoryData data, int index){
        TextView txtNumber = cardView.findViewById(R.id.tvLevelNumber);
        txtNumber.setText("Level "+(index+1));
        FrameLayout lockOverlay = cardView.findViewById(R.id.lockOverlay);
        lockOverlay.setVisibility(data.isOpened()? View.GONE : View.VISIBLE);
        ImageView ivIcon = cardView.findViewById(R.id.ivLevelIcon);
        View viewBackground = cardView.findViewById(R.id.viewBackground);
        ivIcon.setImageResource(data.getImg());

        GradientDrawable gd = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                new int[]{data.getStartColor(), data.getEndColor()}
        );
        gd.setCornerRadius(0f);
        viewBackground.setBackground(gd);

    }

    @Override
    public void navigateToQuiz(int categoryId) {
        Intent intent = new Intent(this, QuizActivity.class);
        intent.putExtra("LEVEL_ID", categoryId);
        startActivity(intent);
    }

    @Override
    public void showProgress(String progressText) {
        tvProgress.setText(progressText);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (presenter != null) {
            presenter.loadData();
        }
    }
}