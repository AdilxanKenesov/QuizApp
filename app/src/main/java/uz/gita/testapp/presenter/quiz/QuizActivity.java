package uz.gita.testapp.presenter.quiz;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.button.MaterialButton;

import java.util.List;

import uz.gita.testapp.R;
import uz.gita.testapp.data.model.QuestionData;
import uz.gita.testapp.presenter.result.ResultActivity;

public class QuizActivity extends AppCompatActivity implements QuizContract.View {
    private QuizContract.Presenter presenter;
    private RadioGroup radioGroup;
    private MaterialButton btnNext, btnPrev;
    private TextView tvLevelInfo, tvQuestionCount, tvQuestionText;
    private ImageView ivQuestionImage;
    private TextView tvSkip;
    private final RadioButton[] options = new RadioButton[4];
    private ShimmerFrameLayout shimmerFrameLayout;
    private View dataLayout;
    private boolean isImageLoaded = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quiz);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        shimmerFrameLayout = findViewById(R.id.shimmer_layout);
        dataLayout = findViewById(R.id.data_layout);

        initViews();

        int levelId = getIntent().getIntExtra("LEVEL_ID", 1);
        presenter = new QuizPresenter(this);

        shimmerFrameLayout.setVisibility(View.VISIBLE);
        shimmerFrameLayout.startShimmer();

        presenter.start(levelId);

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                presenter.saveCurrentState();
                finish();
            }
        });


    }

    private void initViews() {
        radioGroup = dataLayout.findViewById(R.id.radioGroupOptions);
        btnNext = findViewById(R.id.btnNext);
        btnPrev = findViewById(R.id.btnPrev);
        tvLevelInfo = findViewById(R.id.tvCategoryTitle);
        tvQuestionCount = findViewById(R.id.tvQuestionCount);
        tvQuestionText = dataLayout.findViewById(R.id.tvQuestionText);
        ivQuestionImage = dataLayout.findViewById(R.id.ivQuestion);
        ImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finishQuiz());
        tvSkip = findViewById(R.id.tvSkip);
        ImageView btnReset = findViewById(R.id.btnReset);
        btnReset.setOnClickListener(v -> presenter.resetQuiz());

        options[0] = dataLayout.findViewById(R.id.option1);
        options[1] = dataLayout.findViewById(R.id.option2);
        options[2] = dataLayout.findViewById(R.id.option3);
        options[3] = dataLayout.findViewById(R.id.option4);
        tvSkip.setOnClickListener(v -> presenter.skip());

        btnNext.setOnClickListener(v -> presenter.next());
        btnPrev.setOnClickListener(v -> presenter.prev());

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton rb = dataLayout.findViewById(checkedId);
            if (rb != null && rb.isPressed()) {
                presenter.selectOption(rb.getText().toString());
            }
        });

        findViewById(R.id.btnBack).setOnClickListener(v -> finishQuiz());
    }

    @Override
    public void showQuestion(QuestionData data, List<String> shuffledOptions, int position, int total, String selectedAnswer) {
        radioGroup.clearCheck();
        tvQuestionText.setText(data.getQuestion());
        isImageLoaded = false;
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        shimmerFrameLayout.startShimmer();

        Glide.with(this)
                .load(data.getImage())
                .listener(new RequestListener<>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        isImageLoaded = true;
                        checkAndStopShimmer();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        isImageLoaded = true;
                        checkAndStopShimmer();
                        return false;
                    }
                })
                .into(ivQuestionImage);


        for (int i = 0; i < options.length; i++) {
            options[i].setText(shuffledOptions.get(i));
        }


        if (selectedAnswer != null) {
            for (RadioButton rb : options) {
                if (rb.getText().toString().equals(selectedAnswer)) {
                    rb.setChecked(true);
                    break;
                }
            }
        }
    }
    @Override
    public void setNavigationButtons(boolean isFirst, boolean isLast) {
        btnPrev.setVisibility(isFirst ? View.INVISIBLE : View.VISIBLE);
        btnNext.setText(isLast ? "Finish" : "Next");
    }

    @Override
    public void updateHeader(int level, int pos, int total) {
        tvLevelInfo.setText("Level " + level);
        tvQuestionCount.setText(pos + " / " + total);
    }

    @Override
    public void navigateToResult() {
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("LEVEL_ID", getIntent().getIntExtra("LEVEL_ID", 1));
        startActivity(intent);
        finish();
    }

    @Override
    public void finishQuiz() {
        presenter.saveCurrentState();
        finish();
    }

    @Override
    public void setSkipButtonVisibility(boolean isVisible) {
        if (isVisible) {
            tvSkip.setVisibility(View.VISIBLE);
        } else {
            tvSkip.setVisibility(View.GONE);
        }
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void checkAndStopShimmer() {
        if (isImageLoaded) {
            shimmerFrameLayout.stopShimmer();
            shimmerFrameLayout.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.saveCurrentState();
    }
}