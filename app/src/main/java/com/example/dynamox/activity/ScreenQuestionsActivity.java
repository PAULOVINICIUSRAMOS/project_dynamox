package com.example.dynamox.activity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.dynamox.R;
import com.example.dynamox.adapter.AnswerOptionsAdapter;
import com.example.dynamox.databinding.ActivityScreenQuestionsBinding;
import com.example.dynamox.dto.QuestionResponseDto;
import com.example.dynamox.rest.helper.QuestionsHelper;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScreenQuestionsActivity extends AppCompatActivity implements AnswerOptionsAdapter.OnItemClickListener{

    private ActivityScreenQuestionsBinding binding;
    private static QuestionResponseDto questionResponseDto = new QuestionResponseDto();
    private QuestionsHelper questionsHelper = new QuestionsHelper();
    private AnswerOptionsAdapter mAdapter;
    private boolean isRunning = false;
    private int timeRemaining = 30;
    private int counter = 0;
    private int hits;
    private int wrong;
    private Handler handler = new Handler();

    View mRoot;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityScreenQuestionsBinding.inflate(getLayoutInflater());
        mRoot = binding.getRoot();
        mContext = mRoot.getContext();
        setContentView(mRoot);
        getQuestionFromApi();
        startTimer();
        configAnimator();
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
                getQuestionFromApi();
                startTimer();
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
        String selectedAnswer = questionResponseDto.getOptions().get(position);
        mAdapter.setSelectedPosition(position);
        Toast.makeText(this, selectedAnswer, Toast.LENGTH_SHORT).show();
    }

    private void getQuestionFromApi() {
        try {
            Call<QuestionResponseDto> call = questionsHelper.getQuestions();
            call.enqueue(new Callback<QuestionResponseDto>() {
                @Override
                public void onResponse(Call<QuestionResponseDto> call, Response<QuestionResponseDto> response) {
                    if (response.isSuccessful()) {
                        questionResponseDto = response.body();
                        populateView();
                    } else {
                        int errorCode = response.code();
                        System.out.println("Erro na resposta da API. Código: " + errorCode);
                    }
                }

                @Override
                public void onFailure(Call<QuestionResponseDto> call, Throwable t) {
                    System.out.println("Erro na comunicação com a API: " + t.getMessage());
                }
            });
        } catch (Exception e) {
            System.out.println("Ocorreu uma exceção: " + e.getMessage());
        }
    }

    private void configAnimator() {
        Animator animator = AnimatorInflater.loadAnimator(this, R.animator.rotate_animation);
        animator.setTarget(binding.imageScreen);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();
    }
}
