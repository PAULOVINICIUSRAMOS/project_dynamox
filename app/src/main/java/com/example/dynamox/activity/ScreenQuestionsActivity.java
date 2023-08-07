package com.example.dynamox.activity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.dynamox.R;
import com.example.dynamox.adapter.AnswerOptionsAdapter;
import com.example.dynamox.databinding.ActivityScreenQuestionsBinding;
import com.example.dynamox.dto.QuestionResponseDto;
import com.example.dynamox.model.AnswerResponse;
import com.example.dynamox.rest.helper.QuestionsHelper;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScreenQuestionsActivity extends AppCompatActivity implements AnswerOptionsAdapter.OnItemClickListener {

    private ActivityScreenQuestionsBinding binding;
    private static QuestionResponseDto questionResponseDto = new QuestionResponseDto();
    private QuestionsHelper questionsHelper = new QuestionsHelper();
    private AnswerOptionsAdapter mAdapter;
    private boolean isRunning = false;
    private int timeRemaining = 30;
    private int counter = 1;
    private int score;
    private static String selectedAnswer;
    private Handler handler = new Handler();
    private Dialog loadingDialog;
    private Dialog feedbackDialog;

    View mRoot;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showLoadingScreen();
        binding = ActivityScreenQuestionsBinding.inflate(getLayoutInflater());
        mRoot = binding.getRoot();
        mContext = mRoot.getContext();
        setContentView(mRoot);
        getQuestionFromApi();
        configAnimator();
        configDialog();
    }

    private void showLoadingScreen() {
        loadingDialog = new Dialog(this, android.R.style.Theme_Translucent_NoTitleBar);
        View view = getLayoutInflater().inflate(R.layout.activity_splash, null);
        loadingDialog.setContentView(view);
        loadingDialog.setCancelable(false);
        loadingDialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!isRunning) {
            handler.postDelayed(updateTimerRunnable, 1000);
            isRunning = true;
        }
    }

    private Runnable updateTimerRunnable = new Runnable() {
        @Override
        public void run() {
            if (timeRemaining > 0) {
                binding.cronometer.tvTimer.setText(getString(R.string.timer_format, timeRemaining));
                timeRemaining--;
                handler.postDelayed(this, 1000);
            } else {
                startTimer();
                getQuestionFromApi();
            }
        }
    };

    private void startTimer() {
        timeRemaining = 30;
        handler.postDelayed(updateTimerRunnable, 1000);
        isRunning = true;
    }

    private void populateView() {
        if (questionResponseDto != null) {
            binding.txtQuestion.setText(questionResponseDto.getStatement());

            ArrayList<String> listOptions = questionResponseDto.getOptions();
            mAdapter = new AnswerOptionsAdapter(listOptions);
            mAdapter.setOnItemClickListener(this);

            binding.recyclerAnswer.setLayoutManager(new LinearLayoutManager(this));
            binding.recyclerAnswer.setAdapter(mAdapter);
        }
    }

    @Override
    public void onItemClick(int position) {
        selectedAnswer = questionResponseDto.getOptions().get(position);
        mAdapter.setSelectedPosition(position);
        submitAnswer();
    }

    private void getQuestionFromApi() {
        timeRemaining = 30;
        try {
            Call<QuestionResponseDto> call = questionsHelper.getQuestions();
            call.enqueue(new Callback<QuestionResponseDto>() {
                @Override
                public void onResponse(Call<QuestionResponseDto> call, Response<QuestionResponseDto> response) {
                    if (response.isSuccessful()) {
                        questionResponseDto = response.body();
                        populateView();
                        hideLoadingScreen();
                        counter++;
                        if (counter == 10) {
                            Intent intent = new Intent(ScreenQuestionsActivity.this, FinalResultActivity.class);
                            intent.putExtra("score", score);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        int errorCode = response.code();
                        System.out.println("Erro na resposta da API. Código: " + errorCode);
                    }
                }

                @Override
                public void onFailure(Call<QuestionResponseDto> call, Throwable t) {
                    System.out.println("Erro na comunicação com a API: " + t.getMessage());
                    hideLoadingScreen();
                }
            });
        } catch (Exception e) {
            System.out.println("Ocorreu uma exceção: " + e.getMessage());
            hideLoadingScreen();
        }
    }

    private void hideLoadingScreen() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    private void submitAnswer() {
        if (selectedAnswer != null) {
            long questionId = questionResponseDto.getId();
            questionsHelper.submitAnswer(questionId, selectedAnswer).enqueue(new Callback<AnswerResponse>() {
                @Override
                public void onResponse(Call<AnswerResponse> call, Response<AnswerResponse> response) {
                    if (response.isSuccessful()) {
                        AnswerResponse answerResponse = response.body();
                        boolean isCorrect = answerResponse.isResult();
                        showAnswerFeedback(isCorrect);
                        if (isCorrect) {
                            score++;
                        }
                    } else {
                        int errorCode = response.code();
                        System.out.println("Erro na resposta da API. Código: " + errorCode);
                    }
                }

                @Override
                public void onFailure(Call<AnswerResponse> call, Throwable t) {
                    System.out.println("Erro na comunicação com a API: " + t.getMessage());
                }
            });
        }
    }

    private void showAnswerFeedback(boolean isCorrect) {
        String feedbackText;

        if (isCorrect) {
            feedbackText = "Parabens você acertou!";
        } else {
            feedbackText = "Resposta incorreta.";
        }

        TextView txtDialogFeedback = feedbackDialog.findViewById(R.id.txt_dialog_feedback);
        txtDialogFeedback.setText(feedbackText);

        feedbackDialog.show();

        handler.postDelayed(() -> {
            feedbackDialog.dismiss();
            selectedAnswer = null;
            getQuestionFromApi();
        }, 2000);
    }

    private void configAnimator() {
        Animator animator = AnimatorInflater.loadAnimator(this, R.animator.rotate_animation);
        animator.setTarget(binding.imageScreen);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();
    }

    private void configDialog(){
        feedbackDialog = new Dialog(this);
        feedbackDialog.setContentView(R.layout.dialog);
        feedbackDialog.setCancelable(false);
    }
}
