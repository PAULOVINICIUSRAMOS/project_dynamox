package com.example.dynamox.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dynamox.databinding.ActivityScreenQuestionsBinding;
import com.example.dynamox.dto.QuestionResponseDto;
import com.example.dynamox.rest.helper.QuestionsHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScreenQuestionsActivity extends AppCompatActivity {

    private ActivityScreenQuestionsBinding binding;
    private QuestionsHelper questionsHelper = new QuestionsHelper();

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


    private void getQuestionFromApi() {
        try {
            Call<QuestionResponseDto> call = questionsHelper.getQuestions();
            call.enqueue(new Callback<QuestionResponseDto>() {
                @Override
                public void onResponse(Call<QuestionResponseDto> call, Response<QuestionResponseDto> response) {
                    if (response.isSuccessful()) {
                        QuestionResponseDto questionResponseDto = response.body();
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
