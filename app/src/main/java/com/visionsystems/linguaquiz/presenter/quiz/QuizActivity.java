package com.visionsystems.linguaquiz.presenter.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

import java.util.List;

import com.visionsystems.linguaquiz.R;
import com.visionsystems.linguaquiz.data.model.QuestionData;
import com.visionsystems.linguaquiz.presenter.common.MessageDialogFragment;
import com.visionsystems.linguaquiz.presenter.result.ResultActivity;

public class QuizActivity extends AppCompatActivity implements QuizContract.View {
    private QuizContract.Presenter presenter;
    private RadioGroup radioGroup;
    private MaterialButton btnNext, btnPrev;
    private TextView tvLevelInfo, tvQuestionCount, tvQuestionText;
    private ImageView ivQuestionImage;
    private final RadioButton[] options = new RadioButton[4];
    private View dataLayout;

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

        dataLayout = findViewById(R.id.data_layout);

        initViews();

        int levelId = getIntent().getIntExtra("LEVEL_ID", 1);
        boolean isContinue = getIntent().getBooleanExtra("CONTINUE", false);
        presenter = new QuizPresenter(this);

        if (isContinue) {
            showContinueDialog(levelId);
        } else {
            startQuiz(levelId);
        }

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                presenter.saveCurrentState();
                finish();
            }
        });
    }

    private void startQuiz(int levelId) {
        presenter.start(levelId);
    }

    private void showContinueDialog(int levelId) {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_continue_choice, null);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(view)
                .setCancelable(false)
                .create();

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        view.findViewById(R.id.btnContinueGame).setOnClickListener(v -> {
            startQuiz(levelId);
            dialog.dismiss();
        });

        view.findViewById(R.id.btnNewGame).setOnClickListener(v -> {
            presenter.start(levelId);
            presenter.resetQuiz();
            dialog.dismiss();
        });

        dialog.show();
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

        ImageView btnReset = findViewById(R.id.btnReset);
        btnReset.setOnClickListener(v -> presenter.resetQuiz());

        options[0] = dataLayout.findViewById(R.id.option1);
        options[1] = dataLayout.findViewById(R.id.option2);
        options[2] = dataLayout.findViewById(R.id.option3);
        options[3] = dataLayout.findViewById(R.id.option4);

        btnNext.setOnClickListener(v -> presenter.next());
        btnPrev.setOnClickListener(v -> presenter.prev());

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton rb = dataLayout.findViewById(checkedId);
            if (rb != null && rb.isPressed()) {
                presenter.selectOption(rb.getText().toString());
            }
        });
    }

    @Override
    public void showQuestion(QuestionData data, List<String> shuffledOptions, int position, int total, String selectedAnswer) {
        radioGroup.clearCheck();
        tvQuestionText.setText(data.getQuestion());

        ivQuestionImage.setImageResource(data.getImage());

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
    public void showToast(String message) {
        MessageDialogFragment.display(getSupportFragmentManager(), message);
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.saveCurrentState();
    }
}