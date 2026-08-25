package com.visionsystems.linguaquiz.presenter.result;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

import java.util.List;

import com.visionsystems.linguaquiz.R;
import com.visionsystems.linguaquiz.data.model.MyAnswerData;
import com.visionsystems.linguaquiz.presenter.quiz.QuizActivity;

public class ResultActivity extends AppCompatActivity implements ResultContract.View {
    private ResultContract.Presenter presenter;
    private LinearLayout container;
    private MaterialButton btnRestart;
    private TextView tvTotalScore, tvCorrectCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initViews();
        presenter = new ResultPresenter(this);
        int currentLevelId = getIntent().getIntExtra("LEVEL_ID", 1);

        presenter.start(currentLevelId);
    }

    private void initViews() {
        container = findViewById(R.id.container);
        tvTotalScore = findViewById(R.id.tvTotalScore);
        tvCorrectCount = findViewById(R.id.tvCorrectCount);
        btnRestart = findViewById(R.id.btnRestart);

        btnRestart.setOnClickListener(v -> presenter.onRestartClicked());

        findViewById(R.id.btnHome).setOnClickListener(v -> presenter.onHomeClicked());
    }

    @Override
    public void showResults(List<MyAnswerData> list, int correctCount, int totalCount, int percentage) {
        tvTotalScore.setText(percentage + "%");
        tvCorrectCount.setText(correctCount + " / " + totalCount + " correct answers");

        if (percentage < 70) tvTotalScore.setTextColor(Color.RED);
        else tvTotalScore.setTextColor(Color.parseColor("#4CAF50"));

        LayoutInflater inflater = LayoutInflater.from(this);
        container.removeAllViews();

        for (MyAnswerData data : list) {
            View itemView = inflater.inflate(R.layout.item_result, container, false);

            ImageView ivResImage = itemView.findViewById(R.id.ivResImage);
            TextView tvQuestion = itemView.findViewById(R.id.tvResQuestion);
            TextView tvRight = itemView.findViewById(R.id.tvResRightAnswer);
            TextView tvUser = itemView.findViewById(R.id.tvResUserAnswer);

            tvQuestion.setText(data.getQuestion());
            tvRight.setText("Correct answer: " + data.getRightAnswer());
            tvUser.setText("Your answer: " + data.getWrongAnswer());

            ivResImage.setImageResource(data.getImage());

            if (data.getRightAnswer().trim().equalsIgnoreCase(data.getWrongAnswer().trim())) {
                tvUser.setTextColor(Color.parseColor("#4CAF50"));
                tvUser.setText("Your answer: " + data.getWrongAnswer());
            } else {
                tvUser.setTextColor(Color.parseColor("#F44336"));
                tvUser.setText("Your answer: " + data.getWrongAnswer());
            }

            container.addView(itemView);
        }
    }

    @Override
    public void navigateToHome() {
        finish();
    }

    @Override
    public void navigateToQuiz(int levelId) {
        Intent intent = new Intent(this, QuizActivity.class);
        intent.putExtra("LEVEL_ID", levelId);
        startActivity(intent);
        finish();
    }
}