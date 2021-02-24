package com.impact.credopayment.Api.JSONSchema;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ThirdPartySchema extends ApiResponse implements Serializable {

    @SerializedName("code")
    @Expose
    String code;

    @SerializedName("transRef")
    @Expose
    String transRef;



    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTransRef() {
        return transRef;
    }

    public void setTransRef(String transRef) {
        this.transRef = transRef;
    }
}
