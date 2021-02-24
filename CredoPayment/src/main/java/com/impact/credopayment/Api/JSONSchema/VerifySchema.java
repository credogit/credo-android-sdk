package com.impact.credopayment.Api.JSONSchema;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class VerifySchema extends ApiResponse implements Serializable {
    @SerializedName("data")
    @Expose
    String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
