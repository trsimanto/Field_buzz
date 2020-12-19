package com.towhid.fieldbuzz.network.model.form_info.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CvFile {


    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("tsync_id")
    @Expose
    private String tsyncId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTsyncId() {
        return tsyncId;
    }

    public void setTsyncId(String tsyncId) {
        this.tsyncId = tsyncId;
    }

}