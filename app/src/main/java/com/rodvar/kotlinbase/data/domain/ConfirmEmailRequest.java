package com.rodvar.kotlinbase.data.domain;

import com.rodvar.kotlinbase.base.data.BaseAppModel;
import com.rodvar.kotlinbase.data.cloud.API;

/**
 * Created by rodvar on 26/5/17.
 * <p>
 * Security email confirmation request
 */
public class ConfirmEmailRequest extends BaseAppModel {

    public static final String RESOURCE_PATH = "/init/auth/questions?v=1";

    private QuestionRequest questionsRequest;

    public ConfirmEmailRequest(String email) {
        this.questionsRequest = new QuestionRequest(email);
    }

    public class QuestionRequest extends BaseAppModel {

        private String brand = API.Companion.getLOB();
        private String lob = API.Companion.getLOB();
        private String username;

        QuestionRequest(String username) {
            this.username = username;
        }

    }
}
