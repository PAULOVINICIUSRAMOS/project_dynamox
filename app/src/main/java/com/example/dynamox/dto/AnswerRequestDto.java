package com.example.dynamox.dto;

import com.google.gson.annotations.SerializedName;

public class AnswerRequestDto {

    @SerializedName("answer")
    private String answer;

    public AnswerRequestDto() {
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
