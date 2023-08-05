package com.example.dynamox.rest.service;

import com.example.dynamox.dto.QuestionResponseDto;

import retrofit2.Call;
import retrofit2.http.GET;

public interface QuestionsService {

    @GET("question")
    Call<QuestionResponseDto> getQuestion();
}
