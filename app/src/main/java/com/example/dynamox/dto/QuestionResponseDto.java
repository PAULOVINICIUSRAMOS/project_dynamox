package com.example.dynamox.dto;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class QuestionResponseDto {

    @SerializedName("id")
    private String id;

    @SerializedName("statement")
    private String statement;

    @SerializedName("options")
    private ArrayList<String> options;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }
}
