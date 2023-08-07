package com.example.dynamox.rest.service;

import com.example.dynamox.dto.AnswerRequestDto;
import com.example.dynamox.dto.QuestionResponseDto;
import com.example.dynamox.model.AnswerResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface QuestionsService {

    @GET("question")
    Call<QuestionResponseDto> getQuestion();

    @POST("/answer")
    Call<AnswerResponse> submitAnswer(@Query("questionId") long questionId, @Body AnswerRequestDto answer);

}
