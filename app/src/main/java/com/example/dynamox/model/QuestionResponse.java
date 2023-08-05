package com.example.dynamox.model;

import java.util.ArrayList;

public class QuestionResponse {

    private long id;
    private String statement;
    private ArrayList<String> options;

    public QuestionResponse(long id, String statement, ArrayList<String> options) {
        this.id = id;
        this.statement = statement;
        this.options = options;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
