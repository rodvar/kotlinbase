package com.rodvar.kotlinbase.data.domain.security;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import com.rodvar.kotlinbase.base.data.BaseAppModel;

/**
 * Created by rodvar on 14/3/17.
 * <p>
 * Security question request
 */
public class SecurityQuestion extends BaseAppModel {

    @SerializedName(value = "questionsResponse")
    private List<String> questions;

    public SecurityQuestion() {
        super();
        this.questions = new ArrayList<>();
    }

    // API is always returning 1 question
    public String getQuestion() {
        return this.getQuestion(0);
    }

    private String getQuestion(int number) {
        if (number >= this.questions.size())
            throw new IllegalArgumentException("There is no enough questions to fulfill the request of question number " + number);
        return questions.get(number);
    }
}
