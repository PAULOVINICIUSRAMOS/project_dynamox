package com.example.dynamox.rest.helper;


import com.example.dynamox.dto.AnswerRequestDto;
import com.example.dynamox.dto.QuestionResponseDto;
import com.example.dynamox.model.AnswerRequest;
import com.example.dynamox.model.AnswerResponse;
import com.example.dynamox.network.RetrofitClient;
import com.example.dynamox.rest.service.QuestionsService;

import retrofit2.Call;

public class QuestionsHelper {

    private QuestionsService service;

    public QuestionsHelper() {
        service = RetrofitClient.getRetrofitInstance().create(QuestionsService.class);
    }

    public Call<QuestionResponseDto> getQuestions(){
        return service.getQuestion();
    }

    public Call<AnswerResponse> submitAnswer(long questionId, String answer) {
        AnswerRequestDto dto = new AnswerRequestDto();
        dto.setAnswer(answer);
        return service.submitAnswer(questionId, dto);
    }
}

