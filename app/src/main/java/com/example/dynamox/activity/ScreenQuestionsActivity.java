package com.example.dynamox.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.dynamox.adapter.AnswerOptionsAdapter;
import com.example.dynamox.databinding.ActivityScreenQuestionsBinding;
import com.example.dynamox.dto.QuestionResponseDto;
import com.example.dynamox.rest.helper.QuestionsHelper;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScreenQuestionsActivity extends AppCompatActivity {

    private ActivityScreenQuestionsBinding binding;
    private static QuestionResponseDto questionResponseDto = new QuestionResponseDto();
    private QuestionsHelper questionsHelper = new QuestionsHelper();
    private AnswerOptionsAdapter mAdapter;

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
    }

    private void populateView() {
        if (questionResponseDto != null) {
            binding.txtQuestion.setText(questionResponseDto.getStatement());

            ArrayList<String> listOptions = new ArrayList<>();
            listOptions = questionResponseDto.getOptions();
            mAdapter = new AnswerOptionsAdapter(listOptions);
            ArrayList<String> finalListOptions = listOptions;
            mAdapter.setOnItemClickListener(new AnswerOptionsAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    String selectedAnswer = finalListOptions.get(position);
                }
            });
            binding.recyclerAnswer.setLayoutManager(new LinearLayoutManager(this));
            binding.recyclerAnswer.setAdapter(mAdapter);
        }
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
}
