package com.towhid.fieldbuzz.network.model.form_info.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FormInfoRes {
    @SerializedName("cv_file")
    @Expose
    private CvFile cvFile;

    public CvFile getCvFile() {
        return cvFile;
    }

    public void setCvFile(CvFile cvFile) {
        this.cvFile = cvFile;
    }
}
