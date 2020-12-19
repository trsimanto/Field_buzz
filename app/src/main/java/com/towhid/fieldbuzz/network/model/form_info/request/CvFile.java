package com.towhid.fieldbuzz.network.model.form_info.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CvFile {

    @SerializedName("tsync_id")
    @Expose
    private String tsyncId;

    public CvFile(String tsyncId) {
        this.tsyncId = tsyncId;
    }

    public String getTsyncId() {
        return tsyncId;
    }

    public void setTsyncId(String tsyncId) {
        this.tsyncId = tsyncId;
    }

}